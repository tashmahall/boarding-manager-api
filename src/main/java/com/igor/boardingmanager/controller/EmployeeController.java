package com.igor.boardingmanager.controller;

import java.net.URI;
import java.time.LocalDate;

import javax.json.JsonMergePatch;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.igor.boardingmanager.controller.assembler.EmployeeAssembler;
import com.igor.boardingmanager.controller.mappers.dtos.EmployeeDTO;
import com.igor.boardingmanager.controller.patch.MergePatchHelper;
import com.igor.boardingmanager.controller.patch.PatchMediaType;
import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.services.EmployeeService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

@CrossOrigin
@RestController
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService service;
	@Autowired
	private EmployeeAssembler assembler;
	
	private ModelMapper mapper = new ModelMapper();
	@Autowired
	private MergePatchHelper mergePatchHelper;
	
	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public ResponseEntity<EmployeeDTO> addOne(@RequestBody @Valid EmployeeDTO form) {
		Employee employee = mapper.map(form, Employee.class);
		service.save(employee);
		EmployeeDTO dto = assembler.toModel(employee);
		
		URI uri = dto.getLink(IanaLinkRelations.SELF).map(optional -> optional.expand("cpf",dto.getCpf())).map(Link::toUri).orElse(null);

		return ResponseEntity.created(uri).body(dto);
	}
	
	@GetMapping(path = "/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<EmployeeDTO> findOne(@PathVariable ("cpf") String cpf){
		Employee employee = service.findByCpf(cpf).copy();
		EmployeeDTO dto = assembler.toModel(employee);
		
	
		return ResponseEntity.ok(dto);
	}
	@PatchMapping(path = "/{cpf}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = {PatchMediaType.APPLICATION_MERGE_PATCH_VALUE})
	@Transactional
	public ResponseEntity<EmployeeDTO> mergePatch(@PathVariable ("cpf") String cpf, 
			@io.swagger.v3.oas.annotations.parameters.RequestBody(content = 
			@Content(examples = 
				@ExampleObject(value = "{\r\n"
					+ "    \"boarding_date\":\"2021-05-01\",\r\n"
					+ "    \"landing_date\":\"2021-05-10\"\r\n"
					+ "}")
				),required = true)
			@RequestBody 
			JsonMergePatch mergePatchDocument) {
		Employee employee = service.findByCpf(cpf).copy();
		
		EmployeeDTO employeeDTO = mapper.map(employee, EmployeeDTO.class);
		EmployeeDTO employeeDTOPatched = mergePatchHelper.mergePatch(mergePatchDocument,employeeDTO,EmployeeDTO.class);
		
		mapper.map(employeeDTOPatched, employee);
		
		employee = service.updateBoardingDate(employee);
		
		return ResponseEntity.ok(assembler.toModel(employee));
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<Page<EmployeeDTO>> findAll(
			@RequestParam(name = "periodBeginning", required = false) String begining, 
			@RequestParam(name = "periodEnding",required = false) String ending, 
			Pageable pageable ){
		LocalDate ldending = LocalDate.parse(ending);
		LocalDate ldBegining = LocalDate.parse(begining);
		Page<Employee> pageEmployee = service.findAll(ldBegining,ldending, pageable);
		Page<EmployeeDTO> pageEmployeeDTO = assembler.toPage(pageEmployee,pageable);
		return ResponseEntity.ok(pageEmployeeDTO);
	}
}

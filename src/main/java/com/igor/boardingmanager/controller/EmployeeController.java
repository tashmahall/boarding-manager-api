package com.igor.boardingmanager.controller;

import java.net.URI;
import java.time.LocalDateTime;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.igor.boardingmanager.controller.assembler.EmployeeAssembler;
import com.igor.boardingmanager.controller.mappers.dtos.EmployeeDTO;
import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.services.EmployeeService;

@CrossOrigin
@RestController
@RequestMapping("/employees")
public class EmployeeController {
	private ModelMapper mapper = new ModelMapper();
	@Autowired
	private EmployeeService service;
	@Autowired
	private EmployeeAssembler assembler;	
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
		EmployeeDTO employee = mapper.map(service.findByCpf( cpf), EmployeeDTO.class) ;
		return ResponseEntity.ok(employee);
	}
	
	@GetMapping(path = "/",params = {"periodBeginning","periodEnding"})
	public  Page<EmployeeDTO> findAll(
			@RequestParam(name = "periodBeginning")LocalDateTime begining,
			@RequestParam(name = "periodEnding")LocalDateTime ending,
			Pageable pageable
			){
		return null;
	}

}

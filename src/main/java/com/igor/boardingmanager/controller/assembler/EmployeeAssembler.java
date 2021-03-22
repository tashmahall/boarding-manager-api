package com.igor.boardingmanager.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.igor.boardingmanager.controller.EmployeeController;
import com.igor.boardingmanager.controller.mappers.dtos.EmployeeDTO;
import com.igor.boardingmanager.entities.Employee;
@Configuration
public class EmployeeAssembler {
	public EmployeeDTO toModel(Employee employee) {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(employee, EmployeeDTO.class)
				.add(linkTo(methodOn(EmployeeController.class).findOne(employee.getCpf())).withSelfRel())
				.add(linkTo(methodOn(EmployeeController.class).findAll(null, null,null)).withRel("employees"));
	}

	public Page<EmployeeDTO> toPage(Page<Employee> pageEmployee,Pageable pageable) {
		List<EmployeeDTO> listEmployeeDTO = new ArrayList<>();
		pageEmployee.forEach(employee ->
			listEmployeeDTO.add(toModel(employee))
		);
		return new PageImpl<>(listEmployeeDTO, pageable, pageEmployee.getTotalElements());
	}
	
}

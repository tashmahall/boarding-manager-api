package com.igor.boardingmanager.controller.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.igor.boardingmanager.controller.mappers.dtos.EmployeeDTO;
import com.igor.boardingmanager.entities.Employee;
@Component
public class EmployeeMapper {
	
	public EmployeeDTO mapTEmployeeDTO(Employee emplyee) {
		ModelMapper mm = new ModelMapper();
		return mm.map(emplyee, EmployeeDTO.class);
	}
	public Employee mapToEmployee(EmployeeDTO emplyee) {
		ModelMapper mm = new ModelMapper();
		return mm.map(emplyee, Employee.class);
	}
}

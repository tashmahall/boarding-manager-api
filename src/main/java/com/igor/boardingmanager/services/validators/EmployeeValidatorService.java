package com.igor.boardingmanager.services.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.services.validators.validations.EmployeeAlreadyRegisteredValidator;

@Service
public class EmployeeValidatorService {
	@Autowired
	private EmployeeAlreadyRegisteredValidator employeeAlreadyRegisteredValidator;
	
	public Employee validate(Employee employee) {
		return employeeAlreadyRegisteredValidator.apply(employee);
	}

}

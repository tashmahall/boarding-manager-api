package com.igor.boardingmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.repositories.EmployeeRepository;
import com.igor.boardingmanager.services.exceptions.ResourceNotFoundException;
import com.igor.boardingmanager.services.validators.EmployeeValidatorService;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository repository;
	@Autowired
	private EmployeeValidatorService validator;
	public Employee save(Employee employee) {
		validator.validate(employee);
		return repository.saveAndFlush(employee);
	}
	public Employee findByCpf(String cpf) {
		Employee employee =  repository.findByCpf(cpf);
		if(employee==null) {
			throw new ResourceNotFoundException("the CPF ["+cpf+"] there isn't in the database. ");
		}
		return employee;
	}

}

package com.igor.boardingmanager.services.validators.validations;

import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.repositories.EmployeeRepository;
import com.igor.boardingmanager.services.exceptions.InvalidEmployeeException;

@Service
public class EmployeeAlreadyRegisteredValidator implements UnaryOperator<Employee> {
	@Autowired
	private EmployeeRepository repository;

	@Override
	public Employee apply(Employee t) {
		
		if(repository.existsByCpf(t.getCpf())) {
			throw new InvalidEmployeeException("The employee CPF ["+t.getCpf()+"] already bean registered.");
		}
		return t;
	}

}

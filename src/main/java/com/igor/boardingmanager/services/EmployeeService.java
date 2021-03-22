package com.igor.boardingmanager.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.repositories.EmployeeRepository;
import com.igor.boardingmanager.services.exceptions.ResourceNotFoundException;
import com.igor.boardingmanager.services.validators.BoardingPeriodDateResearcherValidator;
import com.igor.boardingmanager.services.validators.EmployeeValidatorService;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository repository;
	@Autowired
	private EmployeeValidatorService validator;
	@Autowired
	private BoardingPeriodDateResearcherValidator boardingPeriodDateResearcherValidator;
	public Employee save(Employee employee) {
		validator.saveNewEmployeeValidate(employee);
		return repository.saveAndFlush(employee);
	}
	public Employee findByCpf(String cpf) {
		Employee employee =  repository.findByCpf(cpf);
		if(employee==null) {
			throw new ResourceNotFoundException("the CPF ["+cpf+"] there isn't in the database. ");
		}
		return employee;
	}
	public Employee updateBoardingDate(Employee employee) {
		validator.setNewEmployeeBoardingDate(employee);
		return repository.saveAndFlush(employee);
		
	}
	public Page<Employee> findAll(LocalDate begining, LocalDate ending, Pageable pageable) {
		if(begining==null && ending == null) {
			return repository.findAll(pageable);
		}
			boardingPeriodDateResearcherValidator.validate(begining,ending);
			return repository.findByPeriodBoarding(begining, ending, pageable);
	}

}

package com.igor.boardingmanager.services.validators.validations;

import java.time.LocalDate;
import java.util.function.UnaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.repositories.EmployeeRepository;
import com.igor.boardingmanager.services.exceptions.InvalidEmployeeException;

@Service
public class Employee7DaysMinLandedValidator implements UnaryOperator<Employee> {
	@Autowired
	private EmployeeRepository repository;

	@Override
	public Employee apply(Employee t) {
		if(t.getBoardingDate()==null) {
			throw new InvalidEmployeeException("The Boarding date must be defined.");
		}
		LocalDate landingDate = repository.findLandingDateByCpf(t.getCpf());
		if(landingDate==null) {
			return t;
		}
		Long days = java.time.temporal.ChronoUnit.DAYS.between(landingDate, t.getBoardingDate());
		if(days<=7) {
			throw new InvalidEmployeeException("The Employee must have at least 7 days of rest before the next boarding date.");
		}
		return t;
	}

}

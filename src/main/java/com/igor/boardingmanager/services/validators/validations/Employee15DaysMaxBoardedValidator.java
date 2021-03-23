package com.igor.boardingmanager.services.validators.validations;

import java.util.function.UnaryOperator;

import org.springframework.stereotype.Service;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.services.exceptions.InvalidEmployeeException;

@Service
public class Employee15DaysMaxBoardedValidator implements UnaryOperator<Employee> {
	@Override
	public Employee apply(Employee t) {
		if(t.getLandingDate() == null && t.getBoardingDate() == null) {
			return t;
		}

		if(t.getLandingDate() !=null && t.getBoardingDate()==null) {
			return t;
		}
		
		if( t.getLandingDate() == null && t.getBoardingDate() != null ) {
			throw new InvalidEmployeeException("The Landing Date it's needed when a Boarding date is defined.");
		}
		
		if(t.getLandingDate().isBefore(t.getBoardingDate())) {
			throw new InvalidEmployeeException("The Landing Date can not be before the Boarding date.");
		}
		Long days = java.time.temporal.ChronoUnit.DAYS.between(t.getBoardingDate(), t.getLandingDate());
		if(days>15) {
			throw new InvalidEmployeeException("The Employee can be boarded at max 15 days");
		}
		return t;
	}

}

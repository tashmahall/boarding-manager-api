package com.igor.boardingmanager.services.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.services.validators.validations.Employee15DaysMaxBoardedValidator;
import com.igor.boardingmanager.services.validators.validations.Employee7DaysMinLandedValidator;
import com.igor.boardingmanager.services.validators.validations.EmployeeAlreadyRegisteredValidator;

@Service
public class EmployeeValidatorService {
	@Autowired
	private EmployeeAlreadyRegisteredValidator employeeAlreadyRegisteredValidator;
	@Autowired
	private Employee15DaysMaxBoardedValidator employee15DaysMaxBoardedValidator;
	@Autowired
	private Employee7DaysMinLandedValidator employee7DaysMinLandedValidator;
	
	
	public Employee saveNewEmployeeValidate(Employee employee) {
		return employee15DaysMaxBoardedValidator
				.andThen(employeeAlreadyRegisteredValidator)
				.apply(employee);
	}
	public Employee setNewEmployeeBoardingDate(Employee employee) {
		return employee7DaysMinLandedValidator
				.andThen(employee15DaysMaxBoardedValidator)
				.apply(employee);
	}
}

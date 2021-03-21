package com.igor.boardingmanager.services.validators;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.services.validators.validations.Employee15DaysMaxBoardedValidator;
import com.igor.boardingmanager.services.validators.validations.Employee7DaysMinLandedValidator;
import com.igor.boardingmanager.services.validators.validations.EmployeeAlreadyRegisteredValidator;
@ExtendWith(SpringExtension.class)
class EmployeeValidatorServiceTest {
	@InjectMocks
	private EmployeeValidatorService service;
	@Mock
	private EmployeeAlreadyRegisteredValidator employeeAlreadyRegisteredValidator;
	@Mock
	private Employee15DaysMaxBoardedValidator employee15DaysMaxBoardedValidator;
	@Mock
	private Employee7DaysMinLandedValidator employee7DaysMinLandedValidator;
	
	private static Employee employee;
	@BeforeAll
	static void  init() {
		employee = new Employee();
		employee.setCpf("73220880002");
		employee.setFunctionName("oil and gas technician");
		employee.setCompanyName("hallybourton");
		employee.setName("Luigi do Mario");
	}
	@Test
	@DisplayName("Test employee saving method")
	void testSaveNewEmployeeValidate() {
		when(employee15DaysMaxBoardedValidator.andThen(employeeAlreadyRegisteredValidator)).thenReturn(employeeAlreadyRegisteredValidator);
		when(employeeAlreadyRegisteredValidator.apply(employee)).thenReturn(employee);
		Employee test = service.saveNewEmployeeValidate(employee);
		Assertions.assertEquals(employee, test);
	}
	@Test
	@DisplayName("Test New Employee Boarding Date")
	void TestSetNewEmployeeBoardingDate() {
		when(employee7DaysMinLandedValidator.andThen(employee15DaysMaxBoardedValidator)).thenReturn(employee15DaysMaxBoardedValidator);
		when(employee15DaysMaxBoardedValidator.apply(employee)).thenReturn(employee);
		Employee test = service.setNewEmployeeBoardingDate(employee);
		Assertions.assertEquals(employee, test);
	}
}

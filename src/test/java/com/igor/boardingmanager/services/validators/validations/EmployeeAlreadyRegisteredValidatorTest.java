package com.igor.boardingmanager.services.validators.validations;

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
import com.igor.boardingmanager.repositories.EmployeeRepository;
import com.igor.boardingmanager.services.exceptions.InvalidEmployeeException;
@ExtendWith(SpringExtension.class)
class EmployeeAlreadyRegisteredValidatorTest {
	@InjectMocks
	private EmployeeAlreadyRegisteredValidator service;
	@Mock
	private EmployeeRepository repository;
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
	@DisplayName("Test if the Employee already been registered ok")
	void testApplyTrue() {
		when(repository.existsByCpf(employee.getCpf())).thenReturn(false);
		Employee test = service.apply(employee);
		Assertions.assertEquals(employee, test);
	}
	@Test
	@DisplayName("Test if the Employee already been registered Exception")
	void testApplyException() {
		when(repository.existsByCpf(employee.getCpf())).thenReturn(true);
		Assertions.assertThrows(InvalidEmployeeException.class, () -> {
			service.apply(employee);
		});
	}

}

package com.igor.boardingmanager.services;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.repositories.EmployeeRepository;
import com.igor.boardingmanager.services.exceptions.ResourceNotFoundException;
import com.igor.boardingmanager.services.validators.EmployeeValidatorService;
@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {
	@InjectMocks
	private EmployeeService service;
	@Mock
	private EmployeeRepository repository;
	@Mock
	private EmployeeValidatorService validator;
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
	void testSave() {
		when(validator.validate(employee)).thenReturn(employee);
		when(repository.saveAndFlush(employee)).thenReturn(employee);
		Employee test = service.save(employee);
		Assertions.assertEquals(employee, test);
	}
	@Test
	@DisplayName("Test get employee by CPF")
	void testGetOneOk() {
		when(repository.findByCpf(employee.getCpf())).thenReturn(employee);
		Employee test = service.findByCpf(employee.getCpf());
		Assertions.assertEquals(employee, test);
	}
	@Test
	@DisplayName("Test Exception when get employee by CPF")
	void testGetOneException() {
		when(repository.findByCpf(employee.getCpf())).thenReturn(Mockito.nullable(Employee.class));
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findByCpf(employee.getCpf());
		});
	}
}

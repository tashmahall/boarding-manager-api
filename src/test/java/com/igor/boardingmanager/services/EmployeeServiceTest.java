package com.igor.boardingmanager.services;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

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
	private static Employee employeeBoardingDateOk;
	
	@BeforeAll
	static void  init() {
		LocalDate now = LocalDate.now();
		employee = new Employee();
		employee.setCpf("73220880002");
		employee.setFunctionName("oil and gas technician");
		employee.setCompanyName("hallybourton");
		employee.setName("Luigi do Mario");
		
		employeeBoardingDateOk = new Employee();
		employeeBoardingDateOk.setCpf("73220880002");
		employeeBoardingDateOk.setFunctionName("oil and gas technician");
		employeeBoardingDateOk.setCompanyName("hallybourton");
		employeeBoardingDateOk.setName("Luigi do Mario");
		employeeBoardingDateOk.setBoardingDate(now.plusDays(8));
		employeeBoardingDateOk.setLandingDate(now);
	}
	
	@Test
	@DisplayName("Test employee saving method")
	void testSave() {
		when(validator.saveNewEmployeeValidate(employee)).thenReturn(employee);
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
	
	@Test
	@DisplayName("Test Update employee Boarding Date")
	void testUpdateBoardingDate() {
		when(validator.setNewEmployeeBoardingDate(employeeBoardingDateOk)).thenReturn(employeeBoardingDateOk);
		when(repository.saveAndFlush(employeeBoardingDateOk)).thenReturn(employeeBoardingDateOk);
		Employee test = service.updateBoardingDate(employeeBoardingDateOk);
		Assertions.assertEquals(employeeBoardingDateOk, test);
	}
}

package com.igor.boardingmanager.services.validators.validations;

import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.repositories.EmployeeRepository;
import com.igor.boardingmanager.services.exceptions.InvalidEmployeeException;
@ExtendWith(SpringExtension.class)
class Employee7DaysMinLandedValidatorTest {
	@InjectMocks
	private Employee7DaysMinLandedValidator service;
	@Mock
	private EmployeeRepository repository;

	private static Employee employeeBoardingDateOk;
	private static Employee employeeBoardingDateNull;
	private static Employee repositoryEmployeeLandingDateNull;
	private static Employee employeeBoardingDateEqual7Days;
	
	private static LocalDate period7Days;
	private static LocalDate now;
	@BeforeAll
	static void  init() {
		now = LocalDate.now();
		period7Days = now.plusDays(7);
		
		employeeBoardingDateOk = new Employee();
		employeeBoardingDateOk.setCpf("73220880002");
		employeeBoardingDateOk.setFunctionName("oil and gas technician");
		employeeBoardingDateOk.setCompanyName("hallybourton");
		employeeBoardingDateOk.setName("Luigi do Mario");
		employeeBoardingDateOk.setBoardingDate(now.plusDays(8));
		
		employeeBoardingDateNull = new Employee();
		employeeBoardingDateNull.setCpf("73220880002");
		employeeBoardingDateNull.setFunctionName("oil and gas technician");
		employeeBoardingDateNull.setCompanyName("hallybourton");
		employeeBoardingDateNull.setName("Luigi do Mario");
		
		repositoryEmployeeLandingDateNull = new Employee();
		repositoryEmployeeLandingDateNull.setCpf("73220880002");
		repositoryEmployeeLandingDateNull.setFunctionName("oil and gas technician");
		repositoryEmployeeLandingDateNull.setCompanyName("hallybourton");
		repositoryEmployeeLandingDateNull.setName("Luigi do Mario");
		
		employeeBoardingDateEqual7Days = new Employee();
		employeeBoardingDateEqual7Days.setCpf("73220880002");
		employeeBoardingDateEqual7Days.setFunctionName("oil and gas technician");
		employeeBoardingDateEqual7Days.setCompanyName("hallybourton");
		employeeBoardingDateEqual7Days.setName("Luigi do Mario");
		employeeBoardingDateEqual7Days.setBoardingDate(now.plusDays(7));
		
	}
	@Test
	void testApplyEmployeeBoardingDateNull() {
		
		Assertions.assertThrows(InvalidEmployeeException.class,()->{
			service.apply(employeeBoardingDateNull);
		});
	}
	@Test
	void testApplyRepositoryEmployeeLandingDateNull() {
		when(repository.findLandingDateByCpf(employeeBoardingDateOk.getCpf())).thenReturn(Mockito.nullable(LocalDate.class));
		Employee teste = service.apply(employeeBoardingDateOk);
		Assertions.assertEquals(employeeBoardingDateOk, teste);
	}
	@Test
	void testApplyBoardingDateEqual7Days() {
		when(repository.findLandingDateByCpf(employeeBoardingDateEqual7Days.getCpf())).thenReturn(period7Days);
		Assertions.assertThrows(InvalidEmployeeException.class,()->{
			service.apply(employeeBoardingDateEqual7Days);
		});
	}
	@Test
	void testApplyEmployeeBoardingDateOk() {
		when(repository.findLandingDateByCpf(employeeBoardingDateOk.getCpf())).thenReturn(now);
		Employee teste = service.apply(employeeBoardingDateOk);
		Assertions.assertEquals(employeeBoardingDateOk, teste);
	}	
}

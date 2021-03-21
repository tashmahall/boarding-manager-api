package com.igor.boardingmanager.services.validators.validations;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.igor.boardingmanager.entities.Employee;
import com.igor.boardingmanager.services.exceptions.InvalidEmployeeException;
@ExtendWith(SpringExtension.class)
class Employee15DaysMaxBoardedValidatorTest {
	@InjectMocks
	private Employee15DaysMaxBoardedValidator service;
	private static Employee employeeLandingAndBoardingDateNull;
	private static Employee employeeLandingAndBoardingDateOk;
	private static Employee employeeLandingDateNullAndBoardingDateOk;
	private static Employee employeeLandingDateBefordBoardingDate;
	private static Employee employeeLandingDateandBoardingDateLessThan15;
	private static Employee employeeLandingDateOkandBoardingDateNull;
	@BeforeAll
	static void  init() {
		LocalDate now = LocalDate.now();
		
		employeeLandingAndBoardingDateNull = new Employee();
		employeeLandingAndBoardingDateNull.setCpf("73220880002");
		employeeLandingAndBoardingDateNull.setFunctionName("oil and gas technician");
		employeeLandingAndBoardingDateNull.setCompanyName("hallybourton");
		employeeLandingAndBoardingDateNull.setName("Luigi do Mario");
		
		employeeLandingAndBoardingDateOk = new Employee();
		employeeLandingAndBoardingDateOk.setCpf("73220880002");
		employeeLandingAndBoardingDateOk.setFunctionName("oil and gas technician");
		employeeLandingAndBoardingDateOk.setCompanyName("hallybourton");
		employeeLandingAndBoardingDateOk.setName("Luigi do Mario");
		employeeLandingAndBoardingDateOk.setBoardingDate(now);
		employeeLandingAndBoardingDateOk.setLandingDate(now.plusDays(15));
		
		employeeLandingDateNullAndBoardingDateOk = new Employee();
		employeeLandingDateNullAndBoardingDateOk.setCpf("73220880002");
		employeeLandingDateNullAndBoardingDateOk.setFunctionName("oil and gas technician");
		employeeLandingDateNullAndBoardingDateOk.setCompanyName("hallybourton");
		employeeLandingDateNullAndBoardingDateOk.setName("Luigi do Mario");
		employeeLandingDateNullAndBoardingDateOk.setBoardingDate(now);
		
		employeeLandingDateBefordBoardingDate = new Employee();
		employeeLandingDateBefordBoardingDate.setCpf("73220880002");
		employeeLandingDateBefordBoardingDate.setFunctionName("oil and gas technician");
		employeeLandingDateBefordBoardingDate.setCompanyName("hallybourton");
		employeeLandingDateBefordBoardingDate.setName("Luigi do Mario");
		employeeLandingDateBefordBoardingDate.setBoardingDate(now.plusDays(15));
		employeeLandingDateBefordBoardingDate.setLandingDate(now);
		
		employeeLandingDateandBoardingDateLessThan15 = new Employee();
		employeeLandingDateandBoardingDateLessThan15.setCpf("73220880002");
		employeeLandingDateandBoardingDateLessThan15.setFunctionName("oil and gas technician");
		employeeLandingDateandBoardingDateLessThan15.setCompanyName("hallybourton");
		employeeLandingDateandBoardingDateLessThan15.setName("Luigi do Mario");
		employeeLandingDateandBoardingDateLessThan15.setBoardingDate(now);
		employeeLandingDateandBoardingDateLessThan15.setLandingDate(now.plusDays(16));
		
		employeeLandingDateOkandBoardingDateNull = new Employee();
		employeeLandingDateOkandBoardingDateNull.setCpf("73220880002");
		employeeLandingDateOkandBoardingDateNull.setFunctionName("oil and gas technician");
		employeeLandingDateOkandBoardingDateNull.setCompanyName("hallybourton");
		employeeLandingDateOkandBoardingDateNull.setName("Luigi do Mario");
		employeeLandingDateOkandBoardingDateNull.setLandingDate(now.plusDays(15));
		
	}
	@Test
	void testApplyLandingAndBoardingDateNull() {
		Employee test = service.apply(employeeLandingAndBoardingDateNull);
		Assertions.assertEquals(employeeLandingAndBoardingDateNull, test);
	}
	@Test
	void testApplyLandingAndBoardingDateOk() {
		Employee test = service.apply(employeeLandingAndBoardingDateOk);
		Assertions.assertEquals(employeeLandingAndBoardingDateOk, test);
		Assertions.assertEquals(employeeLandingAndBoardingDateOk.getBoardingDate(), test.getBoardingDate());
		Assertions.assertEquals(employeeLandingAndBoardingDateOk.getLandingDate(), test.getLandingDate());
	}
	@Test
	void testApplyLandingDateNullAndBoardingDateOk() {
		Assertions.assertThrows(InvalidEmployeeException.class,() -> {
			service.apply(employeeLandingDateNullAndBoardingDateOk);
		});
	}
	@Test
	void testApplyLandingDateBefordBoardingDate() {
		Assertions.assertThrows(InvalidEmployeeException.class,() -> {
			service.apply(employeeLandingDateBefordBoardingDate);
		});
	}
	@Test
	void testApplyLandingDateandBoardingDateLessThan15() {
		Assertions.assertThrows(InvalidEmployeeException.class,() -> {
			service.apply(employeeLandingDateandBoardingDateLessThan15);
		});
	}
	@Test
	void testApplyLandingDateOkandBoardingDateNull() {
		Employee test = service.apply(employeeLandingDateOkandBoardingDateNull);
		Assertions.assertEquals(employeeLandingDateOkandBoardingDateNull, test);
	}
}

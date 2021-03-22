package com.igor.boardingmanager.services.validators.validations;

import static com.igor.boardingmanager.services.validators.BoardingPeriodDateResearcherValidator.BEGINNING;
import static com.igor.boardingmanager.services.validators.BoardingPeriodDateResearcherValidator.ENDING;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.igor.boardingmanager.services.exceptions.InvalidEmployeeException;
@ExtendWith(SpringExtension.class)
class PeriodDateResearcherEndingBiggerBeginningValidatorTest {
	@InjectMocks
	private PeriodDateResearcherEndingBiggerBeginningValidator service;
	private static Map<String, LocalDate> periodException;
	private static Map<String, LocalDate> periodOk;
	@BeforeAll
	static void  init() {
		LocalDate now = LocalDate.now();
		periodException = new TreeMap<>();
		periodException.put(ENDING, now);
		periodException.put(BEGINNING, now.plusDays(14));
		
		periodOk = new TreeMap<>();
		periodOk.put(ENDING, now.plusDays(14));
		periodOk.put(BEGINNING, now);
	}
	@Test
	void testApplyException() {
		Assertions.assertThrows(InvalidEmployeeException.class,()->{
			service.apply(periodException);
		});
	}
	
	@Test
	void testApplyOk() {
		Map<String, LocalDate> teste =service.apply(periodOk);
		Assertions.assertEquals(periodOk, teste);
		
	}
}

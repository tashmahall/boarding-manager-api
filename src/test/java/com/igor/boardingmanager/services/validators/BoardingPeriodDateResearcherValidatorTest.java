package com.igor.boardingmanager.services.validators;

import static com.igor.boardingmanager.services.validators.BoardingPeriodDateResearcherValidator.BEGINNING;
import static com.igor.boardingmanager.services.validators.BoardingPeriodDateResearcherValidator.ENDING;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.igor.boardingmanager.services.validators.validations.PeriodDateResearcherEndingBiggerBeginningValidator;
@ExtendWith(SpringExtension.class)
class BoardingPeriodDateResearcherValidatorTest {
	@InjectMocks
	private BoardingPeriodDateResearcherValidator service;
	@Mock
	private PeriodDateResearcherEndingBiggerBeginningValidator periodDateResearcherEndingBiggerBeginningValidator;
	private static Map<String, LocalDate> periodOk;
	private static LocalDate now;
	private static LocalDate nowPlus14;
	@BeforeAll
	static void  init() {
		now = LocalDate.now();
		nowPlus14 = now.plusDays(14);
		periodOk = new TreeMap<>();
		periodOk.put(ENDING, nowPlus14);
		periodOk.put(BEGINNING, now);
	}
	@Test
	void testValidate() {
		when(periodDateResearcherEndingBiggerBeginningValidator.apply(periodOk)).thenReturn(periodOk);
		Map<String,LocalDate> teste = service.validate(now, nowPlus14);
		Assertions.assertEquals(periodOk, teste);
	}

}

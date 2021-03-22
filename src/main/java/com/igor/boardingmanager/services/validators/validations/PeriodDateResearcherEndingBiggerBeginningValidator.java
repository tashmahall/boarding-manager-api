package com.igor.boardingmanager.services.validators.validations;

import static com.igor.boardingmanager.services.validators.BoardingPeriodDateResearcherValidator.BEGINNING;
import static com.igor.boardingmanager.services.validators.BoardingPeriodDateResearcherValidator.ENDING;

import java.time.LocalDate;
import java.util.Map;
import java.util.function.UnaryOperator;

import org.springframework.stereotype.Service;

import com.igor.boardingmanager.services.exceptions.InvalidEmployeeException;
@Service
public class PeriodDateResearcherEndingBiggerBeginningValidator implements UnaryOperator<Map<String,LocalDate>>  {

	@Override
	public Map<String, LocalDate> apply(Map<String, LocalDate> t) {
		if(t.get(BEGINNING).isAfter(t.get(ENDING))){
			throw new InvalidEmployeeException("The Beginnig of the period to research que not be after the Ending");
		}
		return t;
	}

}

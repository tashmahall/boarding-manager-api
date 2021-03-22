package com.igor.boardingmanager.services.validators;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.igor.boardingmanager.services.validators.validations.PeriodDateResearcherEndingBiggerBeginningValidator;

@Service
public class BoardingPeriodDateResearcherValidator {
	public static final String BEGINNING = "beginningDate";
	public static final String ENDING = "endingDate";
	@Autowired
	private PeriodDateResearcherEndingBiggerBeginningValidator periodDateResearcherEndingBiggerBeginningValidator;
	public Map<String, LocalDate> validate (@NonNull LocalDate begining, @NonNull LocalDate ending) {
		Map<String,LocalDate> map = new TreeMap<>();
		map.put(BEGINNING, begining);
		map.put(ENDING, ending);
		return periodDateResearcherEndingBiggerBeginningValidator.apply(map);
		
	}

}

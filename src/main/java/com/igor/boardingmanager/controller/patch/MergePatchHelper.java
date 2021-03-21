package com.igor.boardingmanager.controller.patch;

import javax.json.JsonMergePatch;
import javax.json.JsonValue;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.igor.boardingmanager.controller.mappers.dtos.EmployeeDTO;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class MergePatchHelper {
	private final ObjectMapper mapper = new ObjectMapper().setDefaultPropertyInclusion(Include.NON_NULL)
			.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
			.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).findAndRegisterModules();
	public EmployeeDTO mergePatch(JsonMergePatch mergePatchDocument, EmployeeDTO employeeDTO, Class<EmployeeDTO> class1) {
		JsonValue jsonValue = mapper.convertValue(employeeDTO, JsonValue.class);
		JsonValue jsonValuePatched = mergePatchDocument.apply(jsonValue);
		return mapper.convertValue(jsonValuePatched, class1);
	}

}

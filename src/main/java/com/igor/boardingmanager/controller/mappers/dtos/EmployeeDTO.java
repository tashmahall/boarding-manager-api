package com.igor.boardingmanager.controller.mappers.dtos;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
public class EmployeeDTO extends RepresentationModel<EmployeeDTO> { 
	@CPF(message="Invalid CPF")
	private String cpf;
	
	private String name;
	
	private String companyName;
	
	private String functionName;
	
	private LocalDate boardingDate;
	
	private LocalDate landingDate;

}

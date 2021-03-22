package com.igor.boardingmanager.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "EMPLOYEES")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"cpf","name"})
@JsonIgnoreProperties(value = {"id"}, ignoreUnknown = true)
public class Employee implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@JsonIgnore
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "CPF", length = 11, nullable = false, unique = true)
	private String cpf;
	
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@Column(name = "COMPANY_NAME", nullable = false)
	private String companyName;
	
	@Column(name = "FUNCTION_NAME", nullable = false)
	private String functionName;
	
	@Column(name = "BOARDING_DATE", nullable = true)
	private LocalDate boardingDate;
	
	@Column(name = "LANDING_DATE", nullable = true)
	private LocalDate landingDate;
	
	public Employee copy(){
		Employee clazz = new Employee();
		clazz.boardingDate=this.boardingDate;
		clazz.companyName = this.companyName;
		clazz.cpf = this.cpf;
		clazz.functionName = this.functionName;
		clazz.id = this.id;
		clazz.landingDate = this.landingDate;
		clazz.name = this.name;
		return clazz;
	}
}
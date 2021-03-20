package com.igor.boardingmanager.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.igor.boardingmanager.entities.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>, PagingAndSortingRepository<Employee, UUID>{
	boolean existsByCpf(@Param("cpf") String cpf);
	Employee findByCpf(@Param("cpf") String cpf);

}

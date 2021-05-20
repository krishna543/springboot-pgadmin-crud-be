package com.ecommerce.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.springboot.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}

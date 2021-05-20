package com.ecommerce.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.springboot.exception.ResourceNotFoundException;
import com.ecommerce.springboot.model.Employee;
import com.ecommerce.springboot.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

	//Step1- Inject the repository (EmployeeRepository)
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//get all employees
	//need to add crossOrigin since xmlhttpResponse is being blocked in angular cli
	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
	
	//need to add crossOrigin since xmlhttpResponse is being blocked in angular cli
	@CrossOrigin(origins = "http://localhost:4200")
	//creating a post API for sending employee and posting it into db
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee) {
		return employeeRepository.save(employee);
		
	}
	
	//need to add crossOrigin since xmlhttpResponse is being blocked in angular cli
	@CrossOrigin(origins = "http://localhost:4200")

	//To get single Employee by Id - Rest API
	@GetMapping("/employees/{id}")
	
	//we are returning response and employee by id so we need to write ReponseEntity<Employee>
	public ResponseEntity<Employee> getEmployeebyId(@PathVariable Long id) {
		
		
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("employee not exits with id:"+id));
		
		return ResponseEntity.ok(employee);
	}
	
	//need to add crossOrigin since xmlhttpResponse is being blocked in angular cli
	@CrossOrigin(origins = "http://localhost:4200")	
	//update employee Rest API
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee (@PathVariable Long id,@RequestBody Employee employeeDetails) {
		
		//first get the details of existing record of employee
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("employee not exits with id"+id));
		
	// Now set that values of existing first name, last name and email as new ones from employeeDetails
		employee.setFirsName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmailId(employeeDetails.getEmailId());
		
	//now override using save method of JPA repository
		Employee updatedEmployee = employeeRepository.save(employee);
		
		return ResponseEntity.ok(updatedEmployee);
		
	}
	//need to add crossOrigin since xmlhttpResponse is being blocked in angular click
	@CrossOrigin(origins = "http://localhost:4200")
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		
		//first get the details of existing record of employee
		Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("employee not exits with id"+id));
		
		//now delete the specific employee
		employeeRepository.delete(employee);
		
		//to send a response if deleted - True
		Map<String, Boolean> response  = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
			
	
	}	
	
	
}

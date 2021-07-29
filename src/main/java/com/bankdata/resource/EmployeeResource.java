package com.bankdata.resource;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankdata.constant.MiscConstants;
import com.bankdata.domain.Employee;
import com.bankdata.dto.EmployeeDTO;
import com.bankdata.repository.EmployeeRepository;
import com.bankdata.service.EmployeeService;
import com.codahale.metrics.annotation.Timed;

@RestController
@RequestMapping("/api")
public class EmployeeResource {

	private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

	@Autowired 
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/createEmployee")
	@Timed
	public ResponseEntity<?> createNewEmployee(@RequestBody EmployeeDTO employeeDto) throws Exception {
		if (employeeDto.getId() != null) {
			return new ResponseEntity<>("Employee already exist",HttpStatus.NOT_IMPLEMENTED);
		}else if(null!=employeeRepository.findOneByNameAndRecordStatusNot(employeeDto.getName(),MiscConstants.RECORD_STATUS_DELETED)) {
			return new ResponseEntity<>("Duplicate employee name",HttpStatus.IM_USED);
		}else {
			employeeService.createEmployee(employeeDto);
			return new ResponseEntity<>("Employee created successfully",HttpStatus.OK);
		}
	}
	
	@PutMapping("/updateEmployee")
	@Timed
	public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDto) throws Exception {
		employeeService.updateEmployee(employeeDto);
		return new ResponseEntity<>("Employee "+employeeDto.getName()+" updated successfully",HttpStatus.OK);
	}
	
	@GetMapping("/employees")
	@Timed
	public List<Employee> getAllEmployees(){
		return employeeService.getAllEmployees();
	}
	
	
	
	@GetMapping("/employee/{id}")
	@Timed
	public Optional<Employee> getEmployee(@PathVariable String id){
		log.info("getEmployee");
		return employeeRepository.findById(id);
	}
	
	@DeleteMapping("/deleteEmployee/{id}")
	@Timed
	public ResponseEntity<?> deleteEmployee(@PathVariable String id) throws Exception {
		return new ResponseEntity<>(employeeService.deleteEmployee(id),HttpStatus.OK);
	}
}

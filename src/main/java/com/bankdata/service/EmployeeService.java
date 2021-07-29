package com.bankdata.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankdata.constant.MiscConstants;
import com.bankdata.domain.Employee;
import com.bankdata.dto.EmployeeDTO;
import com.bankdata.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private final Logger log = LoggerFactory.getLogger(EmployeeService.class); 
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public Employee createEmployee(EmployeeDTO empDTO){
		Employee emp=new Employee();
		BeanUtils.copyProperties(empDTO, emp);
		employeeRepository.save(emp);
		return emp;
	}

	public String deleteEmployee(String id) {
		String msg = "Employee delete successfully";
		Optional<Employee> objEmp = employeeRepository.findById(id);
		if(objEmp.isPresent()) {
			Employee emp = objEmp.get();
			emp.setRecordStatus(2);
			employeeRepository.save(emp);
			msg = "Employee "+emp.getName()+" delete successfully";
		}
		return msg;
	}

	public List<Employee> getAllEmployees() {
		List<Employee> emps = employeeRepository.findByRecordStatusNot(MiscConstants.RECORD_STATUS_DELETED);
		return emps;
	}

	public Employee updateEmployee(EmployeeDTO empDTO){
		Employee emp=new Employee();
		BeanUtils.copyProperties(empDTO, emp);
		emp.setRecordStatus(MiscConstants.RECORD_STATUS_UPDATED);
		employeeRepository.save(emp);
		return emp;
	}
}

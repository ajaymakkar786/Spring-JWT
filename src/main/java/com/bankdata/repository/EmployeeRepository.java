package com.bankdata.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bankdata.domain.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee,String> {
	Employee findOneByNameAndRecordStatusNot(String empName,int recordStatus);
	List<Employee> findByRecordStatusNot(int recordStatus);
	
}

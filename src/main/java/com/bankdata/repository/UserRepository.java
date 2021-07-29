package com.bankdata.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bankdata.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	
	User findByUsername(String name);
}

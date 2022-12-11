package com.stockanalyzer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stockanalyzer.model.UserModel;


@Repository
public interface UserRepo extends CrudRepository<UserModel, Integer>{
	
	UserModel findByEmail(String email);

}

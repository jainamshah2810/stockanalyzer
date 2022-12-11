package com.stockanalyzer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stockanalyzer.model.Role;

@Repository
public interface RoleRepo extends CrudRepository<Role, Integer>{

}

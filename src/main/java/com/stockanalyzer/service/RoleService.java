package com.stockanalyzer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockanalyzer.model.Role;
import com.stockanalyzer.repository.RoleRepo;

@Service
public class RoleService {
	
	@Autowired
	RoleRepo repo;
	
	public Role saveRole(Role role) {
		return repo.save(role);
	}

}

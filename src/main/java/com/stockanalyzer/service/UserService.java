package com.stockanalyzer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.stockanalyzer.model.UserModel;
import com.stockanalyzer.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	UserRepo repo;

	public List<UserModel> getAllUser() {
		List<UserModel> users = new ArrayList<>();
		repo.findAll().forEach(user -> users.add(user));
		return users;
	}

	public UserModel getUserById(int id) {
		if (repo.existsById(id)) {
			return repo.findById(id).get();
		}
		return null;
	}

	public UserModel saveUser(UserModel user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		System.out.println(user.getRoles().toString());
		UserModel insertUser = repo.save(user);
		return insertUser;
	}

	public boolean deleteUser(int id) {
		Boolean isExists = repo.existsById(id);
		if (isExists) {
			repo.deleteById(id);
		}
		return isExists;
	}

	public UserModel updateUser(UserModel user, int id) {
		UserModel tempUser = getUserById(id);
		if (tempUser != null) {
			tempUser.setEmail(user.getEmail());
			tempUser.setName(user.getName());
			tempUser.setPassword(user.getPassword());
			return repo.save(tempUser);
		}
		return null;
	}

}

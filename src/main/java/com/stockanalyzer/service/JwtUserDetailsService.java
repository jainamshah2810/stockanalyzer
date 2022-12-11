package com.stockanalyzer.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stockanalyzer.model.UserModel;
import com.stockanalyzer.repository.UserRepo;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel user = repo.findByEmail(username);

		if (user != null) {
			return new User(user.getEmail(), user.getPassword(), user.getAuthorities());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
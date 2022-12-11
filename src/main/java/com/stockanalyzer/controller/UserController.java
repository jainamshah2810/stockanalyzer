package com.stockanalyzer.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stockanalyzer.config.JwtUtil;
import com.stockanalyzer.dto.JwtRequestDto;
import com.stockanalyzer.dto.JwtResponseDto;
import com.stockanalyzer.dto.ResponseDto;
import com.stockanalyzer.model.Role;
import com.stockanalyzer.model.UserModel;
import com.stockanalyzer.service.JwtUserDetailsService;
import com.stockanalyzer.service.RoleService;
import com.stockanalyzer.service.UserService;

@RestController
@RequestMapping("/api/")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@GetMapping("user")
	@PreAuthorize("hasAnyAuthority('ADMIN')")
	public List<UserModel> getAllUsers() {
		return userService.getAllUser();
	}

	@GetMapping("user/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable int id) {
		UserModel user = userService.getUserById(id);
		if (user != null) {
			return new ResponseEntity<Object>(user, HttpStatus.OK);
		}

		return new ResponseEntity<Object>(new ResponseDto(HttpStatus.NOT_FOUND.value(), "user not found"),
				HttpStatus.OK);
	}

	@DeleteMapping("user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		Boolean isExists = userService.deleteUser(id);
		if (isExists) {
			return new ResponseEntity<Object>(new ResponseDto(HttpStatus.NO_CONTENT.value(), "User Deleted !!"),
					HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new ResponseDto(HttpStatus.BAD_REQUEST.value(), "user not found"),
				HttpStatus.OK);
	}

	@PutMapping("user/{id}")
	public ResponseEntity<Object> updateUser(@PathVariable int id, @RequestBody UserModel user) {
		UserModel newUser = userService.updateUser(user, id);
		if (newUser != null) {
			return new ResponseEntity<Object>(new ResponseDto(HttpStatus.OK.value(), "User Updated !!"), HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new ResponseDto(HttpStatus.NOT_IMPLEMENTED.value(), "Something Went Wrong !!"),
				HttpStatus.OK);
	}

	@PostMapping("/register")
	public ResponseEntity<Object> saveUser(@RequestBody UserModel user) {
		UserModel newUser = userService.saveUser(user);
		if (newUser != null) {
			return new ResponseEntity<Object>(new ResponseDto(HttpStatus.OK.value(), "User Added !!"), HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new ResponseDto(HttpStatus.NOT_IMPLEMENTED.value(), "Something Went Wrong !!"),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDto authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponseDto(token));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	@PostMapping("role")
	public ResponseEntity<Object> saveRole(@RequestBody Role role) {
		Role newRole = roleService.saveRole(role);
		if (newRole != null) {
			return new ResponseEntity<Object>(new ResponseDto(HttpStatus.OK.value(), "Role Added !!"), HttpStatus.OK);
		}
		return new ResponseEntity<Object>(new ResponseDto(HttpStatus.NOT_IMPLEMENTED.value(), "Somthing Went Wrong !!"),
				HttpStatus.OK);
	}

}

package com.mk.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mk.entity.MyUser;
import com.mk.repository.MyUserRepository;

@RestController
@RequestMapping("/authentication")
public class MyUserController {

	@Autowired
	private MyUserRepository repository;
	@Autowired
	private PasswordEncoder encoder;

	@PostMapping("/save")
	public MyUser saveUser(@RequestBody MyUser myUser) {
		myUser.setPassword(encoder.encode(myUser.getPassword()));
		return repository.save(myUser);
	}

	@GetMapping("/id{id}")
	public MyUser findUserById(@PathVariable("/id") Integer id) {
		Optional<MyUser> optional = repository.findById(id);
		if (optional.isEmpty()) {
			throw new NoSuchElementException("No user found with this id.");
		}
		return optional.get();
	}

	@GetMapping("/home")
	public String home() {
		return "Home page...";
	}

	@GetMapping("/user")
	public String user() {
		return "User page...";
	}

	@GetMapping("/admin")
	public String admin() {
		return "Admin page...";
	}
	
	@GetMapping("/login")
	public String login() {
		return "Login page...";
	}
}

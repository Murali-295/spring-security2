package com.mk.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mk.entity.MyUser;
import com.mk.repository.MyUserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private MyUserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MyUser> optional = repository.findByUserName(username);
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException(username);

		}
		MyUser myUser = optional.get();
		return User.builder().username(myUser.getUserName()).password(myUser.getPassword()).roles(getRoles(myUser)).build();

	}

	private String[] getRoles(MyUser myUser) {
		if (myUser.getRole() == null) {
			return new String[]{"USER"};
		}
			return myUser.getRole().split(",");
		}

	}


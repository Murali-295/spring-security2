package com.mk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mk.entity.MyUser;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {
 
	public Optional<MyUser> findByUserName(String name);
}

package com.jphilips.taskmanagementv2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jphilips.taskmanagementv2.entity.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Long>{
	
	Optional<MyUser> findByUsername(String username);
}

package com.nt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nt.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByEmailAndUsername(String email, String username);

	boolean existsByUsernameAndPassword(String username, String password);

	User findByUsername(String username);


	User findByEmail(String email);


}

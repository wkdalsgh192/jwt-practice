package com.caterpie.timeletter.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.caterpie.timeletter.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	User findByEmail(String email);
	
	User findByEmailAndPassword(String email, String password);

	@Transactional
	@Modifying
	@Query(value="update user set  name= ?, password = ?, salt = ?, phone =? where user_id = ?", nativeQuery=true)
	void updateUser(String name, String password, String salt, String phone, int userId);
	
	@Transactional
	@Modifying
	void deleteById(int userId);
	
	@EntityGraph(attributePaths = "authorities")
	Optional<User> findOneWithAuthoritiesByEmail(String email);
	
}


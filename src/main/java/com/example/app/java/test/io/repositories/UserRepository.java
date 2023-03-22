package com.example.app.java.test.io.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.app.java.test.io.entities.UserEntity;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByLogin(String login);
	UserEntity findByuserId(String userId);

}

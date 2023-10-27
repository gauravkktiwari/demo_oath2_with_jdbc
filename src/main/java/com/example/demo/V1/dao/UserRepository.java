package com.example.demo.V1.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM oauth_client_details WHERE client_id=:clientId", nativeQuery = true)
	public void removeClientDetails(@Param("clientId") String clientId);
}
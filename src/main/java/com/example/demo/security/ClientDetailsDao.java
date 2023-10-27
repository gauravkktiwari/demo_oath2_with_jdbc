package com.example.demo.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.common.model.ClientEntity;

@Repository
public interface ClientDetailsDao extends JpaRepository<ClientEntity, Long> {

	ClientEntity findByClientId(String clientId);

}

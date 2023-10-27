package com.example.demo.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
	
	ROLE_USER,ROLE_ADMIN;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return name();
	}

}

package com.example.demo.V1.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.V1.dao.UserRepository;
import com.example.demo.common.model.User;

@RestController
public class UserV1Controller {

	@Autowired
    private AuthorizationServerTokenServices tokenServices;
	
    @Autowired
    private UserRepository userRepository; 
    
    @GetMapping("/api/getToken")
    public String getAccessToken() {
        // Exchange the authorization code for an access token
       // String accessToken = oAuth2RestTemplate.getAccessToken(authorizationCode).getValue();

        // You can now use the access token for API requests or store it as needed
        return "Access Token: APi";
    }

    @GetMapping("/rest/getToken")
    public String getAccessToken1() {
        // Exchange the authorization code for an access token
       // String accessToken = oAuth2RestTemplate.getAccessToken(authorizationCode).getValue();

        // You can now use the access token for API requests or store it as needed
        return "Access Token: Rest";
    }
    
    @PostMapping("/oauth/token2")
    public ResponseEntity<?> token(@RequestBody MultiValueMap<String, String> formParams) {
        // Process the token request using form data
    	System.out.println("UserV1Controller.token():::"+formParams);
    	try {
    	OAuth2AccessToken token = tokenServices.createAccessToken(new OAuth2Authentication(
            new OAuth2Request(formParams.toSingleValueMap(), "mobapp", null, true, null, null, null, null, null),
            new UsernamePasswordAuthenticationToken("username", "password", new ArrayList<>())
        ));
        return ResponseEntity.ok(token);
    	} catch (OAuth2Exception e) {
    		e.printStackTrace();
    	}catch (Exception e) {
            // Handle other exceptions
            // Log the error for debugging
           // logger.error("Internal server error: " + e.getMessage());
    		e.printStackTrace();
            // Return a generic error response to the client
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "server_error");
            errorResponse.put("error_description", "An internal server error occurred.");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
		return null;
    }
    
    @PostMapping("/rest/getAccessToken")
	public Map<String, Object> getAccessToken(@RequestBody Map<String, Object> map) {
		System.out.println("UserV1Controller.getAccessToken()::"+map);
		String clientId = map.get("clientId").toString();
		String clientSecret = map.get("clientSecret").toString();
		String username = map.get("username").toString();
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		OAuth2AccessToken token;
		String password = "";
		MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
		try {
			
			User user = userRepository.findByUsername(username);
			
			if(user == null) {
				response.put("status", "0");
				response.put("Token", "");
				return response;
			}
			
			password = user.getPassword();
			
			formParams.add("grant_type", "refresh_token");
			formParams.add("client_id", clientId);
			formParams.add("client_secret", clientSecret);
			formParams.add("password", password);
			formParams.add("username", username);
			formParams.add("scope", "read+write");
			formParams.add("redirect_uri", "http://localhost:8080/callback");
			
			token = tokenServices.createAccessToken(new OAuth2Authentication(
		            new OAuth2Request(formParams.toSingleValueMap(), clientId, null, true, null, null, null, null, null),
		            new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>())
		        ));
			
			response.put("status", "1");
			response.put("Token", token);
			
			System.out.println("UserV1Controller.getAccessToken():::"+token);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", "0");
			response.put("Token", "");
		}
	
		return response;

	}
    
    protected boolean isExpired(OAuth2RefreshToken refreshToken) {
		if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
			ExpiringOAuth2RefreshToken expiringToken = (ExpiringOAuth2RefreshToken) refreshToken;
			return expiringToken.getExpiration() == null
					|| System.currentTimeMillis() > expiringToken.getExpiration().getTime();
		}
		return false;
	}
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username) {
        try {
        	
        	User user = new User();
        	
        	user.setUsername(username);
        	user.setPassword(new BCryptPasswordEncoder().encode(username));
        	
        	userRepository.save(user);
    
            return ResponseEntity.ok("Login successful");
            
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
        }
    }
    
}

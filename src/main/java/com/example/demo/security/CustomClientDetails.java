package com.example.demo.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import com.example.demo.common.model.ClientEntity;

public class CustomClientDetails implements ClientDetails {

	private String clientId;
    private String clientSecret;
    private Set<String> authorizedGrantTypes;
    private Set<String> scopes;
    private Set<String> redirectUris;
    private int accessTokenValiditySeconds;
    private int refreshTokenValiditySeconds;
    
    public CustomClientDetails(ClientEntity clientEntity) {
        this.clientId = clientEntity.getClientId();
        this.clientSecret = clientEntity.getClientSecret();
        this.authorizedGrantTypes = parseStringToList(clientEntity.getAuthorizedGrantTypes());
        this.scopes = parseStringToList(clientEntity.getScope());
        this.accessTokenValiditySeconds = clientEntity.getAccessTokenValiditySeconds();
        this.refreshTokenValiditySeconds = clientEntity.getRefreshTokenValiditySeconds();
        // Map other fields from clientEntity to this CustomClientDetails
    }
    
    private Set<String> parseStringToList(String commaSeparatedString) {
        // Implement logic to parse a comma-separated string into a Set of strings
        // For example, you can split the string and create a HashSet
        // Modify this logic based on your actual data format
        String[] parts = commaSeparatedString.split(",");
        Set<String> set = new HashSet<>(Arrays.asList(parts));
        return set;
    }
    
	@Override
	public String getClientId() {
		// TODO Auto-generated method stub
		return this.clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSecretRequired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getClientSecret() {
		// TODO Auto-generated method stub
		return this.clientSecret;
	}

	@Override
	public boolean isScoped() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<String> getScope() {
		// TODO Auto-generated method stub
		return this.scopes;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		// TODO Auto-generated method stub
		return this.authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		// TODO Auto-generated method stub
		return this.redirectUris;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return this.accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		// TODO Auto-generated method stub
		return this.refreshTokenValiditySeconds;
	}

	@Override
	public boolean isAutoApprove(String scope) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> getAdditionalInformation() {
		// TODO Auto-generated method stub
		return null;
	}

}

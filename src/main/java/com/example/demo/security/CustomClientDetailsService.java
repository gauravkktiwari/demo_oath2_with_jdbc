package com.example.demo.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;

import com.example.demo.common.model.ClientEntity;

@Service
public class CustomClientDetailsService implements ClientDetailsService {

	private static final Log logger = LogFactory.getLog(CustomClientDetailsService.class);
	
	@Autowired
	private ClientDetailsDao clientDetailsDao;
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		// TODO Auto-generated method stub
		ClientDetails details;
		try {
			
			ClientEntity customDetails = clientDetailsDao.findByClientId(clientId);
			
			if (customDetails == null) {
	            throw new ClientRegistrationException("Client not found: " + clientId);
	        }
			
			 details = new CustomClientDetails(customDetails);

		}
		catch (EmptyResultDataAccessException e) {
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}

		return details;
	}

	public void createOrUpdateClientDetails(String clientId, String clientSecret, String authorizedGrantTypes, String scope, 
			String redirectUri, int accessTokenValiditySeconds, int refreshTokenValiditySeconds) {
        
		 ClientEntity customDetails = clientDetailsDao.findByClientId(clientId);
			
		 if (customDetails == null)
	            customDetails = new ClientEntity();
	        
		 customDetails.setClientId(clientId);
		 customDetails.setClientSecret(clientSecret);
		 customDetails.setAuthorizedGrantTypes(authorizedGrantTypes);
		 customDetails.setScope(scope);
		 customDetails.setRedirectUri(redirectUri);
		 customDetails.setAccessTokenValiditySeconds(accessTokenValiditySeconds);
		 customDetails.setRefreshTokenValiditySeconds(refreshTokenValiditySeconds);
		 clientDetailsDao.save(customDetails);
	 }
	
	 public void createOrUpdateClientDetails(ClientDetails clientDetails) {
	        
		 ClientEntity customDetails = clientDetailsDao.findByClientId(clientDetails.getClientId());
			
		 if (customDetails == null)
	            customDetails = new ClientEntity();
	        
		 customDetails.setClientId(clientDetails.getClientId());
		 customDetails.setClientSecret(clientDetails.getClientSecret());
		 customDetails.setAuthorizedGrantTypes(clientDetails.getAuthorizedGrantTypes().toString());
		 customDetails.setScope(clientDetails.getScope().toString());
		 customDetails.setRedirectUri(clientDetails.getRegisteredRedirectUri().toString());
		 clientDetailsDao.save(customDetails);
	 }
}

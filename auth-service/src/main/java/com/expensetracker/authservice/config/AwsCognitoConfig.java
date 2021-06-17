package com.expensetracker.authservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

public class AwsCognitoConfig {

	public static AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {

		AWSCognitoIdentityProvider awsCognitoIdentityProvider = null;
		BasicAWSCredentials awsCreds = new BasicAWSCredentials("AKIAUU63GJAHFELMJYHV",
	 "/hgOZEaNNxz7J4ddhqZPjRXJzKYfc7H61jvK+lLA");
		
		try {
			awsCognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return awsCognitoIdentityProvider;

	}

	

}

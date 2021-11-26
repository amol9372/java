package com.expensetracker.userservice.config;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

public class AwsCognitoConfig {

	public static AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {

		AWSCognitoIdentityProvider awsCognitoIdentityProvider = null;

		try {
			awsCognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.standard().withRegion("ap-south-1")
					.withCredentials(new EnvironmentVariableCredentialsProvider()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return awsCognitoIdentityProvider;

	}

}
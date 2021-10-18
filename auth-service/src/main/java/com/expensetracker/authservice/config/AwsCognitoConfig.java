package com.expensetracker.authservice.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;

public class AwsCognitoConfig {

	public static AWSCognitoIdentityProvider getAmazonCognitoIdentityClient() {

		AWSCognitoIdentityProvider awsCognitoIdentityProvider = null;

		try {
			awsCognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder.standard().withRegion("ap-south-1")
					.withCredentials(new DefaultAWSCredentialsProviderChain()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return awsCognitoIdentityProvider;

	}

}

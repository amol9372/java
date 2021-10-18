package com.expensetracker.authservice.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.expensetracker.authservice.dto.CognitoSignInUser;
import com.expensetracker.authservice.dto.CognitoSignupUser;

public class CognitoAuthUtils {

	private static final String APP_CLIENT_ID = System.getenv("APP_CLIENT_ID");
	private static final String USER_POOL_ID = System.getenv("USER_POOL_ID");

	public static List<AttributeType> getAttributeMap(CognitoSignupUser cognitoSignupUser) {
		Class<?> cognitoUserClass = cognitoSignupUser.getClass();

		var fields = cognitoUserClass.getDeclaredFields();

		return Arrays.stream(fields).map(field -> {
			field.setAccessible(true);
			Field annotatedField = field.getAnnotation(Field.class);

			AttributeType attributeType = new AttributeType().withName(annotatedField.name())
					.withValue((String) getFieldValue(field, cognitoSignupUser));

			return attributeType;

		}).collect(Collectors.toList());
	}

	public static AdminInitiateAuthResult authenticateUser(CognitoSignInUser cognitoSignInUser) {
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AwsCognitoConfig.getAmazonCognitoIdentityClient();
		Map<String, String> authParameters = Map.of("USERNAME", cognitoSignInUser.getUserName(), "PASSWORD",
				cognitoSignInUser.getPassword());

		AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest().withClientId(APP_CLIENT_ID)
				.withAuthFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH).withUserPoolId(USER_POOL_ID)
				.withAuthParameters(authParameters);

		return awsCognitoIdentityProvider.adminInitiateAuth(authRequest);

	}

	public static Object getFieldValue(java.lang.reflect.Field field, CognitoSignupUser cognitoSignupUser) {
		Object cognitoFieldValue = null;

		try {
			return field.get(cognitoSignupUser);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return cognitoFieldValue;
	}

}

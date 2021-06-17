package com.expensetracker.authservice.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.expensetracker.authservice.dto.CognitoSignInUser;
import com.expensetracker.authservice.dto.CognitoSignupUser;

public class CognitoUtils {

	private static final String APP_CLIENT_ID = "78cmeea2ie7om1m4e42u2genus";
	private static final String USER_POOL_ID = "ap-south-1_7vujruAdZ";

	public static void signUpUser(CognitoSignupUser cognitoSignupUser) {
		List<AttributeType> attributes = getAttributeMap(cognitoSignupUser);
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AwsCognitoConfig.getAmazonCognitoIdentityClient();

		SignUpRequest signUpRequest = new SignUpRequest().withClientId(APP_CLIENT_ID).withUserAttributes(attributes);

		var result = awsCognitoIdentityProvider.signUp(signUpRequest);
	}

	public static void confirmSignupUser(String userName, String confirmationCode) {
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AwsCognitoConfig.getAmazonCognitoIdentityClient();

		ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest().withClientId(APP_CLIENT_ID)
				.withUsername(userName).withConfirmationCode(confirmationCode);

		var result = awsCognitoIdentityProvider.confirmSignUp(confirmSignUpRequest);

	}

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

	public static void authenticateUser(CognitoSignInUser cognitoSignInUser) {
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AwsCognitoConfig.getAmazonCognitoIdentityClient();
		Map<String, String> authParameters = Map.of("USERNAME", cognitoSignInUser.getUserName(), "PASSWORD",
				cognitoSignInUser.getPassword());

		AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest().withClientId(APP_CLIENT_ID)
				.withAuthFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH).withUserPoolId(USER_POOL_ID)
				.withAuthParameters(authParameters);

		var result = awsCognitoIdentityProvider.adminInitiateAuth(authRequest);
		
		System.out.println(result.getAuthenticationResult());
		
		

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

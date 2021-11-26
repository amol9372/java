package com.expensetracker.userservice.config;

import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthRequest;
import com.amazonaws.services.cognitoidp.model.AdminInitiateAuthResult;
import com.amazonaws.services.cognitoidp.model.AuthFlowType;
import com.expensetracker.userservice.request.CognitoSignInUserRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeRequest;
import com.amazonaws.services.cognitoidp.model.AdminRespondToAuthChallengeResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.ChallengeNameType;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.expensetracker.userservice.request.CognitoSignupUser;
import com.expensetracker.userservice.request.ResetPasswordChallengeRequest;

public class CognitoUserUtils {

	private static final String APP_CLIENT_ID = System.getenv("APP_CLIENT_ID");
	private static final String USER_POOL_ID = System.getenv("USER_POOL_ID");

	public static AdminInitiateAuthResult authenticateUser(CognitoSignInUserRequest cognitoSignInUser) {
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AwsCognitoConfig.getAmazonCognitoIdentityClient();
		Map<String, String> authParameters = Map.of("USERNAME", cognitoSignInUser.getUserName(), "PASSWORD",
				cognitoSignInUser.getPassword());

		AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest().withClientId(APP_CLIENT_ID)
				.withAuthFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH).withUserPoolId(USER_POOL_ID)
				.withAuthParameters(authParameters);

		return awsCognitoIdentityProvider.adminInitiateAuth(authRequest);

	}

	public static SignUpResult signUpUser(CognitoSignupUser cognitoSignupUser) {
		List<AttributeType> attributes = getAttributeMap(cognitoSignupUser);
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AwsCognitoConfig.getAmazonCognitoIdentityClient();

		SignUpRequest signUpRequest = new SignUpRequest().withClientId(APP_CLIENT_ID)
				.withUsername(cognitoSignupUser.getUserName()).withPassword(cognitoSignupUser.getPassword())
				.withUserAttributes(attributes);

		return awsCognitoIdentityProvider.signUp(signUpRequest);
	}

	public static ConfirmSignUpResult confirmSignupUser(String userName, String confirmationCode) {
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AwsCognitoConfig.getAmazonCognitoIdentityClient();

		var confirmSignUpRequest = new ConfirmSignUpRequest().withClientId(APP_CLIENT_ID).withUsername(userName)
				.withConfirmationCode(confirmationCode);

		return awsCognitoIdentityProvider.confirmSignUp(confirmSignUpRequest);

	}

	public static AdminRespondToAuthChallengeResult respondToChallenge(
			ResetPasswordChallengeRequest passwordChallengeRequest) {
		AWSCognitoIdentityProvider awsCognitoIdentityProvider = AwsCognitoConfig.getAmazonCognitoIdentityClient();
		var adminRespondToAuthChallengeRequest = new AdminRespondToAuthChallengeRequest()
				.withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED).withUserPoolId(USER_POOL_ID)
				.withChallengeResponses(Map.of("NEW_PASSWORD_REQUIRED", passwordChallengeRequest.getNewPassword(),
						"USERRNAME", passwordChallengeRequest.getUsername()))
				.withClientId(APP_CLIENT_ID);

		return awsCognitoIdentityProvider.adminRespondToAuthChallenge(adminRespondToAuthChallengeRequest);
	}

	public static List<AttributeType> getAttributeMap(CognitoSignupUser cognitoSignupUser) {
		Class<?> cognitoUserClass = cognitoSignupUser.getClass();

		var fields = cognitoUserClass.getDeclaredFields();

		return Arrays.stream(fields).filter(field -> field.getAnnotation(Field.class).required()).map(field -> {
			field.setAccessible(true);

			Field annotatedField = field.getAnnotation(Field.class);

			AttributeType attributeType = new AttributeType().withName(annotatedField.name())
					.withValue((String) getFieldValue(field, cognitoSignupUser));

			return attributeType;

		}).collect(Collectors.toList());
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

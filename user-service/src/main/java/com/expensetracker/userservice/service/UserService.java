package com.expensetracker.userservice.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.expensetracker.userservice.config.CognitoUserUtils;
import com.expensetracker.userservice.config.RedisAttribute;
import com.expensetracker.userservice.model.CognitoSignUpResponse;
import com.expensetracker.userservice.model.CognitoSignupUser;
import com.expensetracker.userservice.model.ConfirmCognitoSignUpRequest;
import com.expensetracker.userservice.model.ConfirmSignupResponse;
import com.expensetracker.userservice.model.UserSignupResponse;
import com.expensetracker.userservice.model.UserSignupRequest;

@Service
public class UserService {

	@Autowired
	private Jedis jedisClient;

	private static final String USER_CREATED_NOT_CONFIRMED = "User created successfully, Please confirm the signup process via email";

	public UserSignupResponse signupUser(UserSignupRequest signupRequest) {

		var userSignUpResponse = new UserSignupResponse();

		CognitoSignupUser cognitoSignupUser = new CognitoSignupUser.CognitoUserBuilder(signupRequest.getUsername(),
				signupRequest.getPassword(), signupRequest.getEmail()).withName(signupRequest.getName())
						.withGender(signupRequest.getGender()).build();

		var signupResult = getSignUpResponse(cognitoSignupUser);

		if (Objects.nonNull(signupResult.getSignUpResult())) {

			// user is created in the user pool
			var userAttributeMap = getRedisAttributeMap(signupRequest);
			jedisClient.hmset(signupRequest.getUsername(), userAttributeMap);

			userSignUpResponse.setUserConfirmed(signupResult.getSignUpResult().getUserConfirmed());
			userSignUpResponse.setMessage(USER_CREATED_NOT_CONFIRMED);
			userSignUpResponse.setStatus(HttpStatus.SC_CREATED);

		} else {
			userSignUpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
			userSignUpResponse.setMessage(signupResult.getErrorMessage());
		}

		return userSignUpResponse;

	}

	public ConfirmSignupResponse confirmSignUp(ConfirmCognitoSignUpRequest confirmCognitoSignUpRequest) {
		var result = getConfirmSignUpResponse(confirmCognitoSignUpRequest);
		return result;
	}

	private CognitoSignUpResponse getSignUpResponse(CognitoSignupUser cognitoSignupUser) {

		var cognitoSignupResponse = new CognitoSignUpResponse();

		try {
			var result = CognitoUserUtils.signUpUser(cognitoSignupUser);
			cognitoSignupResponse.setSignUpResult(result);
		} catch (Exception e) {
			cognitoSignupResponse.setErrorMessage(e.getMessage());
		}

		return cognitoSignupResponse;
	}

	private ConfirmSignupResponse getConfirmSignUpResponse(ConfirmCognitoSignUpRequest confirmCognitoSignUpRequest) {
		var result = new ConfirmSignupResponse();
		try {
			CognitoUserUtils.confirmSignupUser(confirmCognitoSignUpRequest.getUsername(),
					confirmCognitoSignUpRequest.getConfirmationCode());
			result.setHttpStatus(HttpStatus.SC_OK);
			result.setMessage("User is confirmed, please login now");
		} catch (Exception e) {
			result.setHttpStatus(HttpStatus.SC_BAD_REQUEST);
			result.setMessage(e.getMessage());
		}

		return result;
	}

	private Map<String, String> getRedisAttributeMap(UserSignupRequest signupRequest) {

		Class<?> cognitoUserClass = signupRequest.getClass();
		var fields = cognitoUserClass.getDeclaredFields();

		var attributeMap = new HashMap<String, String>();

		Arrays.stream(fields).forEach(f -> {
			f.setAccessible(true);
			// RedisAttribute annotatedField = f.getAnnotation(RedisAttribute.class);

			var value = getFieldValue(f, signupRequest);

			if (Objects.nonNull(value)) {
				attributeMap.put(f.getName(), value.toString());
			}
		});

		return attributeMap;
	}

	public static Object getFieldValue(java.lang.reflect.Field field, UserSignupRequest cognitoSignupUser) {
		Object cognitoFieldValue = null;

		try {
			return field.get(cognitoSignupUser);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return cognitoFieldValue;
	}

}

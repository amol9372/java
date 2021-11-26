package com.expensetracker.userservice.serviceImpl;

import com.amazonaws.services.cognitoidp.model.InternalErrorException;
import com.amazonaws.services.cognitoidp.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidp.model.PasswordResetRequiredException;
import com.amazonaws.services.cognitoidp.model.ResourceNotFoundException;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.expensetracker.userservice.config.CognitoUserUtils;
import com.expensetracker.userservice.config.Constants;
import com.expensetracker.userservice.config.JwtUtil;
import com.expensetracker.userservice.entities.db.ExpenseTrackerBean;
import com.expensetracker.userservice.entities.db.ItemType;
import com.expensetracker.userservice.entities.model.UserEntity;
import com.expensetracker.userservice.entities.model.UserPreferences.UserPreferencesBuilder;
import com.expensetracker.userservice.request.CognitoSignInUserRequest;
import com.expensetracker.userservice.request.CognitoSignInUserRequest.CognitoSignInUserBuilder;
import com.expensetracker.userservice.request.CognitoSignUpResponse;
import com.expensetracker.userservice.request.CognitoSignupUser;
import com.expensetracker.userservice.request.ConfirmCognitoSignUpRequest;
import com.expensetracker.userservice.request.ConfirmSignupResponse;
import com.expensetracker.userservice.request.UserSigninRequest;
import com.expensetracker.userservice.request.UserSignupRequest;
import com.expensetracker.userservice.request.UserSignupResponse;
import com.expensetracker.userservice.response.AppToken;
import com.expensetracker.userservice.response.AuthResponse;
import com.expensetracker.userservice.response.UserSigninResponse;
import com.expensetracker.userservice.service.UserService;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private DynamoDbTable<ExpenseTrackerBean> tableMapper;

  public UserSignupResponse createUser(UserSignupRequest signupRequest) {

    var userSignUpResponse = new UserSignupResponse();

    var cognitoSignupUser = new CognitoSignupUser.CognitoUserBuilder(
        signupRequest.getUsername(),
        signupRequest.getPassword(), signupRequest.getEmail()).withName(signupRequest.getName())
        .withGender(signupRequest.getGender()).build();

    var signupResult = signupResponse(cognitoSignupUser);

    if (Objects.nonNull(signupResult.getSignUpResult())) {

      /**
       * user is created in [ Cognito, DynamoDB, Email : User created ]
       */
      createDynamodbUser(cognitoSignupUser);

      userSignUpResponse.setUserConfirmed(signupResult.getSignUpResult().getUserConfirmed());
      userSignUpResponse.setMessage(Constants.USER_CREATED_NOT_CONFIRMED);
      userSignUpResponse.setStatus(HttpStatus.SC_CREATED);

    } else {
      userSignUpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
      userSignUpResponse.setMessage(signupResult.getErrorMessage());
    }

    return userSignUpResponse;
  }

  private ExpenseTrackerBean createDynamodbUser(CognitoSignupUser cognitoSignupUser) {
    var user = new ExpenseTrackerBean();
    var uuid = ItemType.USER.toString().concat(UUID.randomUUID().toString());
    user.setPk(uuid);
    user.setSk(cognitoSignupUser.getUserName());
    user.setCreatedBy("Portal");
    user.setCreatedDate(LocalDateTime.now());
    user.setEmail(cognitoSignupUser.getEmail());
    user.setName(cognitoSignupUser.getName());
    var preferences = new UserPreferencesBuilder().withCurrency("INR").withLanguage("en-us")
        .withMonthlyLimit(
            3000.0).build();
    user.setPreferences(preferences);
    tableMapper.putItem(user);

    return user;
  }

  public ConfirmSignupResponse confirmSignUp(
      ConfirmCognitoSignUpRequest confirmCognitoSignUpRequest) {
    return getConfirmSignUpResponse(confirmCognitoSignUpRequest);
  }

  private CognitoSignUpResponse signupResponse(CognitoSignupUser cognitoSignupUser) {

    var cognitoSignupResponse = new CognitoSignUpResponse();

    try {
      var result = CognitoUserUtils.signUpUser(cognitoSignupUser);
      cognitoSignupResponse.setSignUpResult(result);
    } catch (Exception e) {
      cognitoSignupResponse.setErrorMessage(e.getMessage());
    }

    return cognitoSignupResponse;
  }

  private ConfirmSignupResponse getConfirmSignUpResponse(
      ConfirmCognitoSignUpRequest confirmCognitoSignUpRequest) {
    var result = new ConfirmSignupResponse();
    try {
      CognitoUserUtils.confirmSignupUser(confirmCognitoSignUpRequest.getUsername(),
          confirmCognitoSignUpRequest.getConfirmationCode());
      result.setHttpStatus(HttpStatus.SC_OK);
      result.setMessage(Constants.USER_CONFIRMED);
    } catch (Exception e) {
      result.setHttpStatus(HttpStatus.SC_BAD_REQUEST);
      result.setMessage(e.getMessage());
    }

    return result;
  }

  public UserSigninResponse authenticateUser(UserSigninRequest request) {

    var response = new UserSigninResponse();

    CognitoSignInUserRequest cognitoSignInUser = new CognitoSignInUserBuilder().withUsername(
            request.getUserName())
        .withPassword(request.getPassword()).build();

    var authResult = getAuthenticationResult(cognitoSignInUser);
    if (Objects.nonNull(authResult.getAdminInitiateAuthResult())) {

      var adminInitiateAuthResult = authResult.getAdminInitiateAuthResult();

      /**
       * check for challenge
       */
      if (Objects.nonNull(adminInitiateAuthResult.getChallengeName())) {
        response.setErrorMessage(adminInitiateAuthResult.getChallengeName());
        response.setHttpStatus(HttpStatus.SC_FORBIDDEN);
      } else {

        /**
         * get user details from Dynamo DB
         */
        UserEntity userDomain = new UserEntity();

        var accessToken = jwtUtil.createToken(userDomain);
        var appTokens = new AppToken();
        appTokens.setAccessToken(accessToken);
        response.setTokens(appTokens);
        response.setHttpStatus(HttpStatus.SC_OK);
      }

    } else {
      response.setErrorMessage(authResult.getErrorResponse());
      response.setHttpStatus(HttpStatus.SC_UNAUTHORIZED);
    }

    return response;
  }

  private AuthResponse getAuthenticationResult(CognitoSignInUserRequest cognitoSignInUser) {

    var authResponse = new AuthResponse();

    try {
      var adminInitiateAuthResult = CognitoUserUtils.authenticateUser(cognitoSignInUser);
      authResponse.setAdminInitiateAuthResult(adminInitiateAuthResult);

    } catch (ResourceNotFoundException | NotAuthorizedException e) {
      authResponse.setErrorResponse(e.getMessage());

    } catch (UserNotFoundException e) {
      authResponse.setErrorResponse(e.getMessage());

    } catch (InternalErrorException e) {
      authResponse.setErrorResponse(e.getMessage());

    } catch (PasswordResetRequiredException e) {
      authResponse.setErrorResponse(e.getMessage());
    }

    return authResponse;
  }
}

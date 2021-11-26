package com.expensetracker.userservice.response;

public class AppToken {

  private String accessToken;
  private String identityToken;
  private String refreshToken;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accesToken) {
    this.accessToken = accesToken;
  }

  public String getIdentityToken() {
    return identityToken;
  }

  public void setIdentityToken(String identityToken) {
    this.identityToken = identityToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}

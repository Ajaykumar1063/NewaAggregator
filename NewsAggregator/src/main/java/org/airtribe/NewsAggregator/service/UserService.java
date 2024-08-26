package org.airtribe.NewsAggregator.service;

import org.airtribe.NewsAggregator.entity.User;
import org.airtribe.NewsAggregator.exception.TokenExpiredException;
import org.airtribe.NewsAggregator.model.LoginDto;
import org.airtribe.NewsAggregator.model.UserModel;


public interface UserService {
  User registerUser(UserModel userModel);

  User autheticateUser(LoginDto loginDto);

  void createVerificationToken(User user, String token);

  boolean validateTokenAndEnableUser(String token) throws TokenExpiredException;

  String createNewVerificationTokenAndInvalidateOldToken(String oldToken);
}

package org.airtribe.NewsAggregator.controller;

import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;
import org.airtribe.NewsAggregator.entity.User;
import org.airtribe.NewsAggregator.exception.TokenExpiredException;
import org.airtribe.NewsAggregator.model.LoginDto;
import org.airtribe.NewsAggregator.model.LoginResponse;
import org.airtribe.NewsAggregator.model.UserModel;
import org.airtribe.NewsAggregator.service.impl.JwtService;
import org.airtribe.NewsAggregator.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class RegistrationController {

  private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private JwtService jwtService;

  @PostMapping("/register")
  public ResponseEntity<User> register(@RequestBody UserModel userModel, HttpServletRequest request) {
    try {
      User userEntity = userService.registerUser(userModel);
      String token = UUID.randomUUID().toString();
      String applicationUrl = getApplicationUrl(request) + "/verifyRegistration?token=" + token;
      userService.createVerificationToken(userEntity, token);
      log.info("Verification token created for user: {}", userEntity.getEmail());
      log.info("Verification url: {}", applicationUrl);
      return ResponseEntity.ok(userEntity);
    } catch (Exception e) {
      log.error("Error while registering user", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/verifyRegistration")
  public ResponseEntity<String> verifyRegistration(@RequestParam String token) {
    try {
      boolean isValid = userService.validateTokenAndEnableUser(token);
      if (isValid) {
        return ResponseEntity.ok("User enabled successfully");
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
      }
    } catch (Exception e) {
      log.error("Error while verifying registration", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginUserDto) {
    try {
      User authenticatedUser = userService.autheticateUser(loginUserDto);
      String jwtToken = jwtService.generateToken(authenticatedUser);
      LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
      return ResponseEntity.ok(loginResponse);
    } catch (Exception e) {
      log.error("Error while authenticating user", e);
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  private String getApplicationUrl(HttpServletRequest request) {
    return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
  }
}


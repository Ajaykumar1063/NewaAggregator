package org.airtribe.NewsAggregator.controller;

import jakarta.validation.Valid;
import org.airtribe.NewsAggregator.config.JwtUtilConfig;
import org.airtribe.NewsAggregator.entity.User;
import org.airtribe.NewsAggregator.model.UserLoginDTO;
import org.airtribe.NewsAggregator.model.UserRegistrationDTO;
import org.airtribe.NewsAggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilConfig jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO user) {
        userService.registerNewUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody UserLoginDTO loginDTO) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );
        final String jwt = jwtUtil.generateToken(loginDTO.getUsername());
        return ResponseEntity.ok(jwt);
    }
}

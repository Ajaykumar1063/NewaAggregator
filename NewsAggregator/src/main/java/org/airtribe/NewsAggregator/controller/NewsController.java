package org.airtribe.NewsAggregator.controller;

import org.airtribe.NewsAggregator.entity.User;
import org.airtribe.NewsAggregator.model.UserPreferencesDTO;
import org.airtribe.NewsAggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class NewsController {

    @Autowired
    private UserService userService;

    @GetMapping("/preferences")
    public ResponseEntity<?> getPreferences(Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        return ResponseEntity.ok(user.getNewsPreferences());
    }

    @PutMapping("/preferences")
    public ResponseEntity<?> updatePreferences(Principal principal, @RequestBody UserPreferencesDTO preferencesDTO) {
        User user = userService.getUserByUsername(principal.getName());
        user.setNewsPreferences(preferencesDTO.getPreferences());
        userService.save(user);
        return ResponseEntity
}

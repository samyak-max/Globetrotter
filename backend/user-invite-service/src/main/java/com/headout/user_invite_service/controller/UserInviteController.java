package com.headout.user_invite_service.controller;

import com.headout.user_invite_service.model.UserInviteModel;
import com.headout.user_invite_service.service.UserInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-invite")
@CrossOrigin(origins = "*")
public class UserInviteController {

    private final UserInviteService userInviteService;

    @Autowired
    public UserInviteController(UserInviteService userInviteService) {
        this.userInviteService = userInviteService;
    }

    @PostMapping
    public ResponseEntity<UserInviteModel> createUser(
            @RequestParam String username,
            @RequestParam int highScore) {
        UserInviteModel newUser = userInviteService.createUser(username, highScore);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<Integer> getHighScore(@PathVariable String username) {
        try {
            int highScore = userInviteService.getHighScore(username);
            return new ResponseEntity<>(highScore, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{username}")
    public ResponseEntity<UserInviteModel> updateInviteCount(@PathVariable String username) {
        try {
            UserInviteModel updatedUser = userInviteService.updateInviteCount(username);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

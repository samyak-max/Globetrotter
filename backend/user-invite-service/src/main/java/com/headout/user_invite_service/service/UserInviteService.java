package com.headout.user_invite_service.service;

import com.headout.user_invite_service.model.UserInviteModel;
import com.headout.user_invite_service.repository.UserInviteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInviteService {

    private final UserInviteRepository userInviteRepository;

    @Autowired
    public UserInviteService(UserInviteRepository userInviteRepository) {
        this.userInviteRepository = userInviteRepository;
    }

    public UserInviteModel createUser(String username, int highScore) {
        UserInviteModel existingUser = userInviteRepository.findByUsername(username);
        if (existingUser != null) {
            throw new RuntimeException("User already exists");
        }
        UserInviteModel newUser = new UserInviteModel(username, highScore, 0);
        return userInviteRepository.save(newUser);
    }

    public int getHighScore(String username) {
        UserInviteModel user = userInviteRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user.getHighScore();
    }

    public UserInviteModel updateInviteCount(String username) {
        UserInviteModel user = userInviteRepository.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setInviteCount(user.getInviteCount() + 1);
        return userInviteRepository.save(user);
    }
}

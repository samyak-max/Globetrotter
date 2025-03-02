package com.headout.user_invite_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user_invite")
public class UserInviteModel {
    @Id
    private String id;
    private String username;
    private int highScore;
    private int inviteCount;

    public UserInviteModel(String username, int highScore, int inviteCount) {
        this.username = username;
        this.highScore = highScore;
        this.inviteCount = inviteCount;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getInviteCount() {
        return inviteCount;
    }

    public void setInviteCount(int inviteCount) {
        this.inviteCount = inviteCount;
    }
}

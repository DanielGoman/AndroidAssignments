package com.example.firebasetutorial;

import com.google.firebase.database.Exclude;


public class MyUser {

    @Exclude

    public String username;
    public String steps;

    public MyUser() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public MyUser(String username, String steps) {
        this.username = username;
        this.steps = steps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }
}

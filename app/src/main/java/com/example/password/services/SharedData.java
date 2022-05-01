package com.example.password.services;

import com.example.password.models.User;

public class SharedData {
    public static User currentUser = new User("","","","");
    public static void setUser(User user){
        currentUser = user;
    }



}

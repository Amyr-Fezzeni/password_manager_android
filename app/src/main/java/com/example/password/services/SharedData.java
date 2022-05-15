package com.example.password.services;

import android.graphics.Color;

import com.example.password.models.User;

public class SharedData {
    public static User currentUser = new User("","","","");
    public static void setUser(User user){
        currentUser = user;
    }
    public static boolean darkMode = false;
    public static void changeDarkMode(boolean value){ darkMode = value; }



}

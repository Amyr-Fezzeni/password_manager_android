package com.example.password.services;

import android.graphics.Color;

import com.example.password.models.User;

public class SharedData {
    public static User currentUser = new User("","","","");
    public static void setUser(User user){
        currentUser = user;
    }

    public static boolean darkMode = true;
    public static int bgColor = Color.rgb(223,223,223);
    public static void changeDarkMode(boolean value){
        darkMode = value;
        if (darkMode){
            bgColor = Color.rgb(23,23,23);
        }else{
            bgColor = Color.rgb(223,223,223);
        }
    }



}

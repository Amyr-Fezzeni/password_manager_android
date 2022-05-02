package com.example.password.models;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.example.password.R;
import com.example.password.services.Scrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Password {
    private String id;
    private String title;
    private String username;
    private String password;

    public Password(String id ,String title, String username, String password){
        this.id = id;
        this.title = title;
        this.username = username;
        this.password = password;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @NonNull
    public String toString(){
        return String.format("(Id:%s, Title:%s, Username:%s, Password:%s)", id, title, username, password);
    }

    public @DrawableRes
    int  getIconId(){
        if (title.toLowerCase().contains("google")){
        return R.drawable.google;
        }
        if (title.toLowerCase().contains("facebook")){
            return R.drawable.facebook;
        }
        if (title.toLowerCase().contains("youtube")){
            return R.drawable.youtube;
        }
        if (title.toLowerCase().contains("linkedin")){
            return R.drawable.linkedin;
        }
        if (title.toLowerCase().contains("github")){
            return R.drawable.github;
        }
        if (title.toLowerCase().contains("skype")){
            return R.drawable.skype;
        }
        if (title.toLowerCase().contains("snapshat")){
            return R.drawable.snapchat;
        }
        if (title.toLowerCase().contains("twitter")){
            return R.drawable.twitter;
        }
        if (title.toLowerCase().contains("gitlab")){
            return R.drawable.gitlab;
        }
        if (title.toLowerCase().contains("instagram")){
            return R.drawable.instagram;
        }
        if (title.toLowerCase().contains("snapchat")){
            return R.drawable.snapchat;
        }

        return R.drawable.autre;

    }

    public Password(Map<String, Object>data){
        this.id = (String) data.get("id");
        this.title = Scrypt.decrypt(String.valueOf(data.get("title")));
        this.username = Scrypt.decrypt(String.valueOf(data.get("username")));
        this.password = Scrypt.decrypt(String.valueOf(data.get("password")));

    }

    public HashMap<String, Object> toJson(){
        HashMap<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("title", Scrypt.crypt(title) );
        data.put("username", Scrypt.crypt(username) );
        data.put("password", Scrypt.crypt(password) );
        return data;
    }
}

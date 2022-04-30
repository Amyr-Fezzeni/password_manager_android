package com.example.password.models;

import androidx.annotation.NonNull;

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

}

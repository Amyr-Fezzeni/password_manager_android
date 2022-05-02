package com.example.password.models;

import com.example.password.services.Scrypt;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String id, String name, String email, String password){
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Map<String, Object> data){
        this.id = (String) data.get("id");
        this.name = (String) data.get("name");
        this.email = (String) data.get("email");
        this.password = (String) data.get("password");
    }
    public HashMap<String, String> toJson(){
        HashMap<String, String> data = new HashMap<>();
        data.put("id",id);
        data.put("name",name);
        data.put("email",email);
        data.put("password", Scrypt.crypt(password));
        return data;
    }
    public static User fromJson(Map<String, Object> json){
        return new User(json);
    }
    public String getId(){
        return id;
    }
}

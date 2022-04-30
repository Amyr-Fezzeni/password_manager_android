package com.example.password.services;

public class Scrypt {
    static String crypt(String data){
        int key = Math.round(data.length() / 2);
        String s = "";
        for (int i = 0; i < data.length(); i++){
            s += Character.toChars(((int) data.charAt(i)) * key) ;

        }
        return s;
    }

    static String decrypt(String data){
        int key = Math.round(data.length() / 2);
        String s = "";
        for (int i = 0; i < data.length(); i++){
            int charCode = data.charAt(i);
            int rounded = Math.round(charCode/key);
            s += Character.toChars(rounded) ;

        }
        return s;
    }
}

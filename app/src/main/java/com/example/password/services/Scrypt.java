package com.example.password.services;

// for scrypting important data
public class Scrypt {

    public static String crypt(String data){
        int key = Math.round(data.length() / 2);
        String s = "";
        for (int i = 0; i < data.length(); i++){
            s += String.valueOf(Character.toChars(((int) data.charAt(i)) * key));

        }
        return s;
    }

    public static String decrypt(String data){

        int key = Math.round(data.length() / 2);
        String s = "";
        for (int i = 0; i < data.length(); i++){

            int charCode = data.charAt(i);
            int rounded = Math.round(charCode/key);
            s += String.valueOf(Character.toChars(rounded)) ;

        }
        return s;
    }
}

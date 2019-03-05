package com.example.androidchess;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {
    private String generatedPassword = null;

    public String passwordEncryptor(String username, String password){

        try{
            MessageDigest md =  MessageDigest.getInstance("MD5");
            byte[] salt = username.getBytes();
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }


        return generatedPassword;
    }
}

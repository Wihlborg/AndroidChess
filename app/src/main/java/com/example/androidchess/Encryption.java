package com.example.androidchess;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

 class Encryption {
    private String generatedPassword = null;

     String passwordEncryptor(String username, String password){

        try{
            MessageDigest md =  MessageDigest.getInstance("MD5");
            byte[] salt = username.getBytes();
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }


        return generatedPassword;
    }
}

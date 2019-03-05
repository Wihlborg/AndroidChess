package com.example.androidchess;

import android.util.Log;
import java.sql.*;

 class Database {
    private static final String TAG = "Database";
    private static Database db = null;
    private Encryption encrypt = new Encryption();


    private Connection connect = null;

    //Singleton instance of Database
     static Database getInstance(){
        if (db == null){
            db = new Database();
        }

        return db;
    }
    //Database constructor
    private Database() {

        String url = "jdbc:mysql://den1.mysql3.gear.host/myshack?user=myshack&password=liridon!";
        try {
            connect = DriverManager.getConnection(url);
            Log.d(TAG,"Connected to database");
        } catch (SQLException e) {
            Log.d(TAG, "Failed to connect to database");
        }

    }

    //For registering user
     boolean registerUser( String username,String email, String password, int account_id) {
        boolean flag = false;

        String query = "INSERT INTO myshack.user (username, email, password, account_id)" + "VALUES (?, ?, ?, ?)";
        // create the mysql insert preparedStatement
        try(PreparedStatement preparedStmt = connect.prepareStatement(query)) {
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, email);
            preparedStmt.setString(3, encrypt.passwordEncryptor(username,password));
            preparedStmt.setInt(4, account_id);
            preparedStmt.execute();

            flag = true;
            Log.d(TAG,"Registration Successful");

        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, String.valueOf(e));
        }

        return flag;
    }

    //For authentication of logging in into the app as a user
     boolean authenticateUser(String username, String password){
        boolean flag = false;
        String query = "SELECT username, password FROM myshack.user WHERE username = ? AND password = ?";
        try(PreparedStatement preparedStmt = connect.prepareStatement(query)) {
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, encrypt.passwordEncryptor(username,password));

            ResultSet resultSet = preparedStmt.executeQuery();
            resultSet.next();
            String username1 = resultSet.getString("username");
            String password1 = resultSet.getString("password");

            if (username1.equals(username) && password1.equals(encrypt.passwordEncryptor(username,password))){
                flag = true;
                Log.d(TAG,"Authentication successful");
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, String.valueOf(e));

        }

        return flag;
    }



}

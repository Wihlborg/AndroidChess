package com.example.androidchess;

import android.util.Log;
import java.sql.*;

public class Database {
    private static final String TAG = "Database";

    private Connection connect = null;

    //Database constructor
    public Database() {

        String url = "jdbc:mysql://den1.mysql3.gear.host/myshack?user=myshack&password=liridon!";
        try {
            connect = DriverManager.getConnection(url);
            Log.d(TAG,"Connected to database");
        } catch (SQLException e) {
            Log.d(TAG, "Failed to connect to database");
        }

    }

    //For registering user
    public boolean registerUser( String username,String email, String password, int account_id) {
        boolean flag = false;

        String query = "INSERT INTO myshack.user (username, email, password, account_id)" + "VALUES (?, ?, ?, ?)";
        // create the mysql insert preparedStatement
        try(PreparedStatement preparedStmt = connect.prepareStatement(query)) {
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, email);
            preparedStmt.setString(3, password);
            preparedStmt.setInt(4, account_id);
            preparedStmt.execute();

            flag = true;
            Log.d(TAG,"Successful: INSERT  ");

        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, String.valueOf(e));
        }

        return flag;
    }

    //For authentication of logging in into the app as a user
    public boolean authenticateUser(String username, String password){
        boolean flag = false;
        String query = "SELECT username, password FROM myshack.user WHERE username = ? AND password = ?";
        try(PreparedStatement preparedStmt = connect.prepareStatement(query)) {
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);

            ResultSet resultSet = preparedStmt.executeQuery();
            resultSet.next();
            String username1 = resultSet.getString("username");
            String password1 = resultSet.getString("password");

            if (username1.equals(username) && password1.equals(password)){
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

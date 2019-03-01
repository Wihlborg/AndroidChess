package com.example.androidchess;

import java.sql.*;

public class Database {

    private Connection connect = null;

    // database constructor
    public Database(){

        try {
            String url = "jdbc:mysql://den1.mysql3.gear.host/myshack?user=myshack&password=liridon!";
            connect = DriverManager.getConnection(url);
            System.out.println("Connected to database");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database");
        }

    }

    //For registering user
    public void registerUser( String username,String email, String password, int account_id) {

        String query = "INSERT INTO myshack.user (username, email, password, id)" + "VALUES (?, ?, ?, ?)";
        // create the mysql insert preparedStatement
        try(PreparedStatement preparedStmt = connect.prepareStatement(query)) {
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, email);
            preparedStmt.setString(3, password);
            preparedStmt.setInt(4, account_id);

            preparedStmt.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //For authentication of logging in into the app as a user
    public boolean authenticateUser(String username, String password){
        boolean flag = false;
        String query = "SELECT username, password FROM myshack.user where username = ? AND password = ?";
        try(PreparedStatement preparedStmt = connect.prepareStatement(query)) {
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, password);

            ResultSet resultSet = preparedStmt.executeQuery();
            resultSet.next();
            String username1 = resultSet.getString("username");
            String password1 = resultSet.getString("password");

            if (username1.equals(username) && password1.equals(password)){
                flag = true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
    }



}

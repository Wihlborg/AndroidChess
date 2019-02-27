package com.example.androidchess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private Connection connect = null;

    // database constructor
    public Database(){

        try {
            String url = "jdbc:mysql://localhost/shack";
            connect = (Connection) DriverManager.getConnection(url);
            System.out.println("Connected to database");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database");
        }

    }

    //For registering accounts
    public void addUser( String username,String email, String password, int account_id) {

        String query = "insert into user (username, email, password, id)" + "values (?, ?, ?, ?)";
        // create the mysql insert preparedStatement
        try(PreparedStatement preparedStmt = connect.prepareStatement(query)) {
            preparedStmt.setString(1, username);
            preparedStmt.setString(2, email);
            preparedStmt.setString(3, password);
            preparedStmt.setInt(4, account_id);

            preparedStmt.execute();
            //connect.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}

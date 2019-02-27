package com.example.androidchess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {

    private PreparedStatement statement = null;
    private Connection connect = null;
    private String url = "jdbc:mysql://localhost/shack";

    // database constructor
    public Database() throws SQLException {

        try {
            connect = (Connection) DriverManager.getConnection(url);
            System.out.println("Connected to database");
        } catch (SQLException ex) {
            System.out.println("Failed to connect to database");
        }

    }

    public void addUser( String username,String email, String password, int account_id) throws  SQLException{

        String query = "insert into user (username, email, password, id)" + "values (?, ?, ?, ?)";

        // create the mysql insert preparedStatement
        PreparedStatement preparedStmt = connect.prepareStatement(query);
        preparedStmt.setString(1, username);
        preparedStmt.setString(2,email);
        preparedStmt.setString(3,password);
        preparedStmt.setInt(4,account_id);

        preparedStmt.execute();
        connect.close();





    }






}

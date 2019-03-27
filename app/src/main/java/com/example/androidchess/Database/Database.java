package com.example.androidchess.Database;

import android.os.StrictMode;
import android.util.Log;
import com.example.androidchess.Elo;
import com.example.androidchess.User;

import java.sql.*;
import java.util.Random;

public class Database {
    private static final String TAG = "Database";
    private static Database db = null;
    private Encryption encrypt = new Encryption();
    private Connection connect = null;

    //Singleton of Database
    public static Database getInstance(){
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
    public boolean registerUser( String username,String email, String password, int account_id) {
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
            Log.d(TAG,"Registration successful");

        }catch (Exception e){
            e.printStackTrace();
            Log.d(TAG, "Failed registration");
        }

        return flag;
    }

    //For authentication of logging in into the app as a user
     public boolean authenticateUser(String username, String password){
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
            Log.d(TAG,"Failed authentication");

        }

        return flag;
    }

     //Random password generated here
     private String randomGenerated()
     {
         Random random = new Random();
         String randomStrings;

             //Random string generated by chars of length 8
             char[] string = new char[random.nextInt(1)+8];
             for(int j = 0; j < string.length; j++)
             {
                 string[j] = (char)('a' + random.nextInt(26));
             }
             randomStrings = String.valueOf(string);

         return randomStrings;
     }

     public boolean updatePassword( String password){
        String username = User.INSTANCE.getName();
        boolean flag = false;
        String updatePassword = ("UPDATE myshack.user SET password = '"
                + encrypt.passwordEncryptor(username,password) +"' WHERE username = '" + username +"'");

        try(Statement prepared = connect.prepareStatement(updatePassword)){
             prepared.executeUpdate(updatePassword);
             flag = true;

             if (flag){

                 String fetchEmail = "SELECT email FROM myshack.user WHERE username = ?";
                 try(PreparedStatement prp = connect.prepareStatement(fetchEmail)){
                     prp.setString(1, username);
                     ResultSet resultSet = prp.executeQuery();
                     resultSet.next();
                     String email = resultSet.getString("email");
                     Mail.getInstance().sendEmail(email,password,username);
                 }catch (Exception e){
                     e.printStackTrace();
                 }

             }
        }catch (Exception e){
            e.printStackTrace();
        }

        return flag;
     }


    //Forgot password using UPDATE query
    public boolean forgotPassword(String email) {
        boolean flag = false;
        String password = randomGenerated();
        String fetchUser= "SELECT username FROM myshack.user WHERE email = ?";
        try(PreparedStatement prp = connect.prepareStatement(fetchUser)){
            prp.setString(1, email);
            ResultSet resultSet = prp.executeQuery();
            resultSet.next();
            flag = true;
            String username = resultSet.getString("username");
            if (flag) {

                String query = ("UPDATE myshack.user SET password = '" +
                        encrypt.passwordEncryptor(username, password) + "' WHERE email = '" + email + "'");

                try (Statement preparedStatement = connect.prepareStatement(query)) {

                    preparedStatement.executeUpdate(query);

                    flag = true;
                    Log.d(TAG, "Recovery successful");
                    Mail.getInstance().sendEmail(email, password, username);

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }





        } catch (Exception e){
            e.printStackTrace();
        }

        return flag;
     }

    public void updateElo(String usernameA, String usernameB, double winnerA){
         try {
             double eloA = getElo(usernameA), eloB = getElo(usernameB);
             Elo elo = new Elo();

             double winnerB = 0;

             if (winnerA == 0){
                 winnerB = 1;
             } else if (winnerA == (1/2)){
                 winnerB = (1/2);
             }

             double newEloA = elo.getNewRating(eloA, eloB, winnerA);
             double newEloB = elo.getNewRating(eloB, eloA, winnerB);

             Statement statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

             String stringA = String.format("UPDATE myshack.user WHERE username = %s SET elo_rating = %d", usernameA, newEloA);
             String stringB = String.format("UPDATE myshack.user WHERE username = %s SET elo_rating = %d", usernameB, newEloB);
             statement.addBatch(stringA);
             statement.addBatch(stringB);
             statement.executeBatch();

         } catch (SQLException e){
             e.printStackTrace();
         }

     }

    public void addWins(){

        String username = User.INSTANCE.getName();
        String fetchWinner = "UPDATE myshack.user WHERE username = " + username + " SET wins = 1";
        try(Statement ps = connect.prepareStatement(fetchWinner)){
            ps.executeUpdate(fetchWinner);

        }catch (Exception e){
            e.printStackTrace();

        }
    }

    public void addLosses(){
        String username = User.INSTANCE.getName();
        String fetchLoser = "UPDATE myshack.user WHERE username = " + username + " SET loses = 1";
        try(Statement ps = connect.prepareStatement(fetchLoser)){
            ps.executeUpdate(fetchLoser);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public double getElo(String username){
         try {
             PreparedStatement ps = connect.prepareStatement("SELECT elo_rating FROM myshack.user WHERE username = ?");
             ps.setString(1, username);

             ResultSet rs = ps.executeQuery();
             rs.next();
             return rs.getDouble("elo_rating");
         } catch (SQLException e) {
             e.printStackTrace();
             return 1000;
         }

     }

}

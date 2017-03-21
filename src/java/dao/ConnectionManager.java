package dao;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yuanyang
 */
public class ConnectionManager {
//    String url = "jdbc:mysql://localhost:8889/iot";
//        String username = "root";
//        String password = "root";
    private String url;
    private String username;
    private String password;
    
    
    public Connection getConnection() {
        
        
        //Deployment username and password is 
        //Username = root
        //password = 1123581321
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
     }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rentauncuento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author alexv
 */
public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/rentauncuento";
    private static final String user = "java";
    private static final String password = "password";
    private static DatabaseConnection db;
    
    private DatabaseConnection() {
    }
    
    public static DatabaseConnection getDatabaseConnection() {
        if(db == null) {
            db = new DatabaseConnection();
        }
        return db;
    }
    
    public ResultSet sqlStatement(String query) {
        Connection conn = null;
        ResultSet result = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            result = st.executeQuery(query);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.bd;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author diego
 */
public class Conexion {
    private static String bd ="rentauncuento";
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String user="admin_RC";
    private static String password = "1234";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static Connection cx;
    private static Conexion connection;

    private Conexion (){
    }    
    public Connection conectar(){
        try{
        Class.forName(driver);
        cx= DriverManager.getConnection(url+bd,user,password);
        //System.out.println("SI SE CONECTO A LA BASE DE DATOS " + bd);
        
        }catch(ClassNotFoundException |SQLException ex){
            System.out.println("NO SE CONECTO A LA BASE DE DATOS " + bd);
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return cx;
     }
    public void desconectar(){
        try {
            cx.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Conexion getInstance(){
        if(connection == null){
            connection = new Conexion();
            return connection;
        }
        return connection ;
        
    }
    
    
    
    public static void main(String [] args){

        Conexion conexion = new Conexion();
        
        conexion.conectar();
        String sql = "select E_name "
                + "from employees "
                + "where E_Name = 'George';";
        
    }    


}

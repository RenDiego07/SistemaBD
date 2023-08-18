/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.bd;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
/**
 *
 * @author diego
 */
public class AuxAlgorithm {
    
    public static boolean isMembresia(String membresia){
        LinkedList<String> membresias = MembresiaTypes();
        return membresias.contains(membresia);
    }
    private static LinkedList<String> MembresiaTypes(){
        LinkedList<String> membresia = new LinkedList<>();
        String query = "select IDMembresia FROM membresia";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                membresia.add(rs.getNString("IDMembresia"));
            }
            rs.close();
        }catch(SQLException r){
            r.printStackTrace();
        }
        return membresia;
    }
    private static LinkedList<String> getIDUsers(){
        LinkedList<String> IDs = new LinkedList<>();
        String query = "SELECT IDUsuario FROM Usuario";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                IDs.add(rs.getNString("IDUsuario"));
            }
            rs.close();
        }catch(SQLException r){
            r.printStackTrace();
        }
        return IDs;
    }
    public static boolean IDAlreadyInDBUsers(String id){
       LinkedList<String> IDs  = getIDUsers();
       return IDs.contains(id);
    }
}

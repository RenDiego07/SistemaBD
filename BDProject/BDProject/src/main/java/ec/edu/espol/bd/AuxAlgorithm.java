/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.bd;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    private static LinkedList<Integer> getIDPurchases(){
        LinkedList<Integer> list = new LinkedList<>();
        String query = "SELECT IDCompra from compra";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                list.add(rs.getInt("IDCompra"));
            }
        }catch(SQLException r){
            r.printStackTrace();
        }
        return list;
    }
    public static boolean IDAlreadyInDBPurchases(int e){
        LinkedList<Integer> list = getIDPurchases();
        return list.contains(e);
    }
    private static LinkedList<String> getIDPackages(){
        LinkedList<String> list = new LinkedList<>();
        String query = "SELECT ID_Paquete from Paquete";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
               ResultSet rs = statement.executeQuery();
               while(rs.next()){
                   list.add(rs.getString("ID_Paquete"));
                   
               }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    public static boolean IDAlreadyInBDPackages(String id){
        LinkedList<String> list = getIDPackages();
        return list.contains(id);
    }
    public static boolean isCuponValid(int cupon){
        return cupon == 0 || cupon == 1;
    }
    public static boolean isRegaloValid(int regalo){
        return regalo == 0 || regalo == 1;
    }
    public static boolean NumCuentosValid(int cantidad){
        return cantidad > 0;
    }
    public static boolean  validDate(String date){
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try{
            java.util.Date utilDate = dateFormat.parse(date);
            Date sqlDate = new Date(utilDate.getTime());
            return true;
        }catch(ParseException e){
            return false;
        }        
    }
    public static void main(String [] args){
        String fecha = "202d/02/07";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);
        try{
            java.util.Date utilDate = dateFormat.parse(fecha);
            Date sqlDate = new Date(utilDate.getTime());
            System.out.println(true);
        }catch(ParseException e){
            System.out.println(false);
        }
    }
}

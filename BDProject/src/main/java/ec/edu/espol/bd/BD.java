/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package ec.edu.espol.bd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
/**
 *
 * @author diego
 */
public class BD {
    
    public static void main(String[] args) {
        System.out.println("vamos a hacer queries");
        
        try{
            Connection connection = Conexion.getInstance().conectar();
            String str = "SELECT * FROM LIBRO";
            PreparedStatement statement = connection.prepareStatement(str);
           
            ResultSet set = statement.executeQuery();
           // el next() me devuelve true hasta que 
            while(set.next()){      
                System.out.println("IDLibro : "+ set.getNString("IDLibro") + "  Titulo: " + set.getNString("titulo"));
                
            }
            connection.close();
            
            
  
        }catch(SQLException r){
            r.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        Date date = Date.valueOf("2022-05-20");
        Compra c = new Compra(0,date,0,"0603124512",0,8);
        //c.setIDPaquete("TP3");
        //c.setIDMembresia(null);
        //insertCompra(c);
        
        System.out.println("search a package");
        Paquete p = new Paquete("TP3");
        getPackage(p);
        
        
        System.out.println("actualizaron el repartidor alex");
        Repartidor r = new Repartidor("axel","0936654778","Vizuete");
        updateNameDeliveryGuy(r);
        
        
        
        }
    
    public static void insertCompra( Compra purchase){
        try{
            String query = "INSERT INTO COMPRA VALUES (?,?,?,?,?,?,?,?)";
            Connection connection = Conexion.getInstance().conectar();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, purchase.getIdCompra());
            statement.setDate(2, purchase.getFechaCompra());
            statement.setInt(3,purchase.getRegalo());
            statement.setString(4,purchase.getUsuarioID());
            if(purchase.getIDPaquete() != null){
                statement.setString(5, purchase.getIDPaquete());
            }else{
                statement.setNull(5, java.sql.Types.VARCHAR);
            }
            if(purchase.getIDMembresia() != null){
                statement.setString(6,purchase.getIDMembresia());
            }else{
                statement.setNull(6, java.sql.Types.VARCHAR);
            }
            statement.setInt(7, purchase.getCupon());
            statement.setInt(8, purchase.getNumeroCuentos());
            int filas = statement.executeUpdate();
            statement.close();
            connection.close();
            
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void getPackage(Paquete e){
        String query = "Select * from paquete WHERE ID_Paquete = ?";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
            statement.setString(1, e.id_Paquete);
            ResultSet rd = statement.executeQuery();
            while(rd.next()){
                System.out.println("ID_Paquete " + rd.getNString("ID_Paquete") + " costo : " + rd.getFloat("costo") + " tipo :" + rd.getInt("numeroDeCuentos") );
            }
            statement.close();
            rd.close();
            
        }catch(SQLException w){
            w.printStackTrace();
        }
    }
    public static void updateNameDeliveryGuy(Repartidor r){
        String query = "UPDATE repartidor SET nombre = ? WHERE IDRepartidor = ?";
       try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
           statement.setString(1,r.getName() );
           statement.setString(2,r.GetIDRepartidor());
           statement.execute();
           
           
       }catch(SQLException e){
           e.printStackTrace();
       }
    }
    
    public static void getRepartidores(){
        String query = "Select * from Repartidor";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                System.out.println("IDRepartidor : " + rs.getNString("IDRepartidor"));
            }
            rs.close();
            
            
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
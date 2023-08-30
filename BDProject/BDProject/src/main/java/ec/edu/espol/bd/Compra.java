/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.bd;
import java.sql.Date;


/**
 *
 * @author diego
 */
public class Compra {
    private int IDCompra;
    private Date fechaCompra;
    private int regalo;
    private String usuarioID;
    private String IDPaquete;
    private String IDMembresia;

    public int getIDCompra() {
        return IDCompra;
    }
    private int cupon;
    private int numeroCuentos;
    
    public Compra(){
    
    }

    public Compra(int idCompra, Date fechaCompra, int regalo, String usuarioID, int cupon, int numeroCuentos) {
        this.IDCompra = idCompra;
        this.fechaCompra = fechaCompra;
        this.regalo = regalo;
        this.usuarioID = usuarioID;
        this.cupon = cupon;
        this.numeroCuentos = numeroCuentos;
    }
    

    public int getIdCompra() {
        return IDCompra;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public int getRegalo() {
        return regalo;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public String getIDPaquete() {
        return IDPaquete;
    }

    public String getIDMembresia() {
        return IDMembresia;
    }

    public int getCupon() {
        return cupon;
    }

    public int getNumeroCuentos() {
        return numeroCuentos;
    }

    public void setIDPaquete(String IDPaquete) {
        this.IDPaquete = IDPaquete;
    }

    public void setIDMembresia(String IDMembresia) {
        this.IDMembresia = IDMembresia;
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.bd;

/**
 *
 * @author diego
 */
public class Usuario {
    private String IDUsuario;
    private String nombre;
    private String apellido;
    private String contrasena;
    private String telefono;
    private String mail;
    private String membresia;
    private double deudas;

    public Usuario(String IDUsuario, String nombre, String apellido, String contrasena, String telefono, String mail) {
        this.IDUsuario = IDUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.mail = mail;
    }

    public String getIDUsuario() {
        return IDUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getMail() {
        return mail;
    }

    public String getMembresia() {
        return membresia;
    }

    public double getDeudas() {
        return deudas;
    }

    public void setMembresia(String membresia) {
        this.membresia = membresia;
    }

    public void setDeudas(double deudas) {
        this.deudas = deudas;
    }
    
    
    
    
        
}

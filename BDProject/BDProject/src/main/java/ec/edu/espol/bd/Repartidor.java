/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.bd;

/**
 *
 * @author diego
 */
public class Repartidor {
    private String name;
    private String IDRepartidor;
    private String lastName;
    public Repartidor(String name, String IDRepartidor, String lastName){
        this.name = name;
        this.IDRepartidor = IDRepartidor;
        this.lastName = lastName;
    }
    
    public String getName(){
        return this.name;
    }
    public String GetIDRepartidor(){
        return this.IDRepartidor;
    }
    public String getLastName(){
        return this.lastName;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rentauncuento;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author alexv
 */
public class RentaUnCuento {
    private static final Scanner sc = new Scanner(System.in);
    private static DatabaseConnection dataBase = DatabaseConnection.getDatabaseConnection();

    public static void main(String[] args) {
       buildMainMenu();
    }
    
    public static void buildMainMenu() {
        System.out.println("Bienvenido a Renta un Cuento!");
        System.out.println("-----------------------------");
        System.out.println("Escoja una opción: ");
        System.out.println("1. Ver membresías");
        System.out.println("2. Ver paquetes");
        System.out.println("3. Ver usuarios registrados");
        System.out.println("-----------------------------");
        System.out.println("Opción seleccionada: ");
        int opt = sc.nextInt();
        sc.nextLine();
        switch(opt) {
            case 1:
                showMemberships();
                break;
            case 2:
                showPackages();
                break;
            case 3:
                showUsers();
                break;
            default:
                System.out.println("Invalid Option. Press enter");
                sc.nextLine();
                buildMainMenu();
        }
    }
    
    public static void showMemberships() {
        ResultSet memberships = dataBase.sqlStatement("SELECT * FROM membresia");
        System.out.println("--- Membresías ---");
        System.out.println("ID|Precio|Tipo");
        try {
            while(memberships.next()) {
                String id = memberships.getString("IDMembresia");
                String price = memberships.getString("costo");
                String type = memberships.getString("tipo");

                System.out.println(id + ", " + price + ", " + type);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        goBackToMainMenu();
    }
    
    public static void showPackages() {
        ResultSet packages = dataBase.sqlStatement("SELECT * FROM paquete");
        System.out.println("--- Paquetes ---");
        System.out.println("ID|Precio|Tipo");
        try {
            while(packages.next()) {
                String id = packages.getString("IDPaquete");
                String price = packages.getString("costo");
                String type = packages.getString("tipo");

                System.out.println(id + ", " + price + ", " + type);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        goBackToMainMenu();
    }
    
    public static void showUsers() {
        ResultSet users = dataBase.sqlStatement("SELECT * FROM usuario");
        System.out.println("--- Usuarios ---");
        System.out.println("ID|Nombre|Apellido|Membresía|E-mail");
        try {
            while(users.next()) {
                String id = users.getString("IDUsuario");
                String name = users.getString("nombre");
                String lastName = users.getString("apellido");
                String membership = users.getString("Tipomembresia");
                String email = users.getString("mail");

                System.out.println(id + ", " + name + ", " + lastName + ", " + membership + ", " + email);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        goBackToMainMenu();
    }
    
    public static void goBackToMainMenu() {
        System.out.println("Desea volver al menu principal? (S/N): ");
        String opt = sc.nextLine();
        if(opt.equals("S") || opt.equals("s")) {
            buildMainMenu();
        }        
    }
}

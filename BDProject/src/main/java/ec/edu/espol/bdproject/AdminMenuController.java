/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bdproject;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.util.LinkedList;
import java.util.ArrayList;


/**
 * FXML Controller class
 *
 * @author diego
 */
public class AdminMenuController implements Initializable {

    @FXML
    private AnchorPane anchor;
    @FXML
    private ImageView portrait;
    @FXML
    private ComboBox<String> comboBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadImage();
        fillComboBox();
    }


    public void loadImage(){
        try{     
           FileInputStream stream = new FileInputStream("images/retro2.png");
           Image im = new Image(stream,800,577,false,false);
           portrait.setImage(im);
           stream.close();
        }catch(IOException r){
            r.printStackTrace();
        }
    }
    public void fillComboBox(){
        String[] options = {"Usuarios", "Compras", "Libros"};
        LinkedList<String> listOptions = new LinkedList<>(Arrays.asList(options));
        comboBox.getItems().addAll(listOptions);
        comboBox.setOnAction((event)->{
        
            String selected = comboBox.getSelectionModel().getSelectedItem();
            switch(selected){
                case("Usuarios"):
                    switchToUsuario();
                    break;
                case("Compras"):
                    //switchToCompras();
                    break;
                case ("Libros"):
                    // switchToLibros
                    break;
            }
        });
    }
    public void switchToUsuario (){
        try{
            App.setRoot("UserTable");
        }catch(Exception r){
            r.printStackTrace();
        }
    }
    public void switchToCompras(){
        try{
            App.setRoot("CompraQueries");
        }catch(Exception r){
            r.printStackTrace();
        }
    }
   public void switchToLibros(){
        try{
            App.setRoot("LibrosQueries");
        }catch(Exception r){
            r.printStackTrace();
        }
    }    
   
    
    
}

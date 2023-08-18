/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bdproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.IOException;
import javafx.scene.control.Button;


/**
 * FXML Controller class
 *
 * @author diego
 */
public class CoverController implements Initializable {

    @FXML
    private ImageView portrait;
    @FXML
    private Button btContinue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadImage();
        continueHandler();
        setColor();
    }
    
    
    
    public void loadImage(){
        try{     
           FileInputStream stream = new FileInputStream("images/Library.png");
           Image im = new Image(stream,800,577,false,false);
           portrait.setImage(im);
           stream.close();
        }catch(IOException r){
            r.printStackTrace();
        }
    }
    public void continueHandler(){
        btContinue.setOnAction((v)->{
            try{
                App.setRoot("adminMenu");
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
   public void setColor(){
       btContinue.setStyle("-fx-background-color: #D2B48C;");
   }
   
    
    

    
    
}

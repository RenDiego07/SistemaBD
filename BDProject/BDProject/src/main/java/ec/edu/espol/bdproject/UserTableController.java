/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bdproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ec.edu.espol.bd.*;
import java.sql.CallableStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
/**
 * FXML Controller class
 *
 * @author diego
 */
public class UserTableController implements Initializable {

    @FXML
    private Button btAdminMenu;
    @FXML
    private TextField txtIDUsuario;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellido;
    @FXML
    private TextField txtContrasena;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtMail;
    @FXML
    private ComboBox viewsCB;
    @FXML
    private TableColumn<Usuario, String> colIDUsuario;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colApellido;
    @FXML
    private TableColumn<Usuario, String> colContrasena;
    @FXML
    private TableColumn<Usuario, String> colTelefono;
    @FXML
    private TableColumn<Usuario, String> colMail;
    @FXML
    private TableColumn<Usuario, String> colMembresia;
    @FXML
    private TableColumn<Usuario, Double> colDeudas;
    @FXML
    private TableView<Usuario> tableView;
    @FXML
    private Button btInsert;
    @FXML
    private Button btEdit;
    @FXML
    private Button btDelete;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BTswitchToAdmin();
        fillListView();
        setUpViewsCB();
        btInsert.setOnAction((event->{
            if(validTxts()){
                addUser();
            }
            clearTxts();
        }));
        btEdit.setOnAction((event2)->{
            if(ValidTxts4Update()){
                update();
            }
        });
        deleteHandler();

    }
    

    public void BTswitchToAdmin(){
        btAdminMenu.setOnAction((event)->{
            try{
                App.setRoot("adminMenu");
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
    public ObservableList<Usuario> getUsers(){
        ObservableList<Usuario> list = FXCollections.observableArrayList();
        String query = "Select * from usuario";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
           ResultSet rs = statement.executeQuery();
           while(rs.next()){
               Usuario user =createNewUser(rs) ;
               user.setDeudas(rs.getDouble("deudas"));
               user.setMembresia(rs.getNString("TipoMembresia"));
               list.add(user);
           }
        }catch(SQLException r){
            r.printStackTrace();
        }
        return list;
        
    }
    private Usuario createNewUser(ResultSet rs){
        if(rs != null){
            try{
            return new Usuario(rs.getNString("IDUsuario"),rs.getNString("nombre"),rs.getNString("apellido"),rs.getNString("contrasena"),rs.getNString("telefono"),rs.getNString("mail"));
            }catch(SQLException e){
                e.printStackTrace();
            }
          }
        return null;
    }
    private void fillListView(){
        ObservableList<Usuario> list = getUsers();
        colIDUsuario.setCellValueFactory(new PropertyValueFactory<Usuario, String>("IDUsuario"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Usuario, String>("apellido"));
        colContrasena.setCellValueFactory(new PropertyValueFactory<Usuario, String>("contrasena"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Usuario, String>("telefono"));
        colMail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("mail"));
        colMembresia.setCellValueFactory(new PropertyValueFactory<Usuario, String>("membresia"));
        colDeudas.setCellValueFactory(new PropertyValueFactory<Usuario, Double>("deudas"));        
        
        tableView.setItems(list);
    }
    private void addUser(){
        String query = "{call agregarCliente(?,?,?,?,?,?)}";
        try(CallableStatement cs = Conexion.getInstance().conectar().prepareCall(query)){
            
            cs.setString(1,txtIDUsuario.getText());
            cs.setString(2, txtNombre.getText());
            cs.setString(3, txtApellido.getText());
            cs.setString(4, txtContrasena.getText());
            cs.setString(5, txtTelefono.getText());
            cs.setString(6, txtMail.getText());
            cs.execute();
            fillListView();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private boolean validTxts(){
        
        if(txtIDUsuario.getText().isBlank() || txtNombre.getText().isBlank() || txtApellido.getText().isBlank() || txtContrasena.getText().isBlank() || txtTelefono.getText().isBlank() || txtMail.getText().isBlank()){
            txtsEmpty();    
            return false;
         }else if(AuxAlgorithm.IDAlreadyInDBUsers(txtIDUsuario.getText())){
             UserAlreadyExistsInDB();
             return false;
         }
        return true;
    }
    private boolean txtsBlank(){
        return txtIDUsuario.getText().isBlank() || txtNombre.getText().isBlank() || txtApellido.getText().isBlank() || txtContrasena.getText().isBlank() || txtTelefono.getText().isBlank() || txtMail.getText().isBlank();
    }
    private boolean ValidTxts4Update(){
        if(txtIDUsuario.getText().isBlank() || txtNombre.getText().isBlank() || txtApellido.getText().isBlank() || txtContrasena.getText().isBlank() || txtTelefono.getText().isBlank() || txtMail.getText().isBlank()){
          txtsEmpty();
          return false;
        }else if(AuxAlgorithm.IDAlreadyInDBUsers(txtIDUsuario.getText())){
            return true;
        }else{
            UserNotFound();
            return false;
        }
        
    }
    private void txtsEmpty(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. UNO O MUCHOS DE LOS TEXTFIELDS ESTÁNVACIO");
        alert.showAndWait();
    }
    private void UserAlreadyExistsInDB(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. NO SE ADMITEN IDS REPETIDAS");
        alert.showAndWait();
    }
    private void clearTxts(){
        txtIDUsuario.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtContrasena.clear();
        txtTelefono.clear();
        txtMail.clear();
    }
    private void update(){
        String query = "{call actualizarUsuario(?,?,?,?,?,?)}" ;
        try(CallableStatement cs = Conexion.getInstance().conectar().prepareCall(query)){
            
        cs.setString(1, txtIDUsuario.getText());
        cs.setString(2, txtNombre.getText());
        cs.setString(3, txtApellido.getText());
        cs.setString(4, txtContrasena.getText());
        cs.setString(5, txtTelefono.getText());
        cs.setString(6,txtMail.getText());
        cs.execute();
        clearTxts();
        fillListView();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void delete(){
        String query = "call eliminarUsuario(?)";
        try(CallableStatement cs = Conexion.getInstance().conectar().prepareCall(query) ){
            cs.setString(1, txtIDUsuario.getText());
            cs.execute();
            clearTxts();
            fillListView();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private void deleteHandler(){
        btDelete.setOnAction((event)->{
            if(!txtsBlank() && AuxAlgorithm.IDAlreadyInDBUsers(txtIDUsuario.getText())){
                delete();
                
            }else{
                txtsEmpty();
            }
        });
    }
    private void UserNotFound(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. USUARIO NO EXISTE");
        alert.showAndWait();
    }

    @FXML
    private void DeleteHandler(MouseEvent event) {
        Usuario user = tableView.getSelectionModel().getSelectedItem();
        txtNombre.setText(user.getNombre());
        txtIDUsuario.setText(user.getIDUsuario());
        txtApellido.setText(user.getApellido());
        txtContrasena.setText(user.getContrasena());
        txtTelefono.setText(user.getTelefono());
        txtMail.setText(user.getMail());
        
    }
    
    private void setUpViewsCB() {
        viewsCB.getItems().add("Usuarios con membresía");
        viewsCB.getItems().add("Usuarios con deudas");
        viewsCB.getItems().add("Todos los usuarios");
        viewsCB.setOnAction(eh -> {
            String selected = (String) viewsCB.getSelectionModel().getSelectedItem();
            ObservableList<Usuario> users = null;
            switch(selected) {
                case "Usuarios con membresía" :
                    users = getUsersFromQuery("SELECT * FROM usuariosConMembresia");
                    break;
                case "Usuarios con deudas":
                    users = getUsersFromQuery("SELECT * FROM usuariosConDeuda");
                    break;
                case "Todos los usuarios":
                    users = getUsersFromQuery("SELECT * FROM usuario");
            }
            if(tableView.getItems() != null) {
                tableView.getItems().clear();            
            }
            tableView.setItems(users);                 
        });
    }
    
    private ObservableList<Usuario> getUsersFromQuery(String query) {
          ObservableList<Usuario> list = FXCollections.observableArrayList();
          try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while(rs.next()) {
                    Usuario user = createNewUser(rs);
                    user.setDeudas(rs.getDouble("deudas"));
                    user.setMembresia(rs.getNString("TipoMembresia")); 
                    list.add(user);
                }
            }catch(SQLException sql) {
                sql.printStackTrace();
            }     
          return list;
    }
    
}

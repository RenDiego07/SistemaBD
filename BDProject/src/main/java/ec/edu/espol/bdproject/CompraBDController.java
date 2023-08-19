/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bdproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ec.edu.espol.bd.*;
import java.sql.Date;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author diego
 */
public class CompraBDController implements Initializable {
    @FXML
    private TableView<Compra> tableView;
    @FXML
    private TableColumn<Compra, Integer> colIDCompra;
    @FXML
    private TableColumn<Compra, Date> colFechaCompra;
    @FXML
    private TableColumn<Compra, Integer> colRegalo;
    @FXML
    private TableColumn<Compra, String> colUsuarioID;
    @FXML
    private TableColumn<Compra, String> colIDPaquete;
    @FXML
    private TableColumn<Compra, String> colIDMembresia;
    @FXML
    private TableColumn<Compra, Integer> colCupon;
    @FXML
    private TableColumn<Compra, Integer> colCantidadDeCuentos;
    @FXML
    private Button btInsert;
    @FXML
    private Button btEdit;
    @FXML
    private Button btDelete;
    @FXML
    private TextField txtIDCompra;
    @FXML
    private TextField txtFechaCompra;
    @FXML
    private TextField txtRegalo;
    @FXML
    private TextField txtUsuarioID;
    @FXML
    private TextField txtIDPaquete;
    @FXML
    private TextField txtIDMembresia;
    @FXML
    private TextField txtCupon;
    @FXML
    private TextField txtCantidadDeCuentos;
    @FXML
    private Button btAdminMenu;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAdminMenu();
        fillListView();
        fillTxts();
        btInsert.setOnAction((event)->{
            if(ValidInsertion()){
                    if(validMembresiaNPaquete()){
                        addUser();
                        emptyTxts();
                        fillListView();
                        fillTxts();
                    }   
            }
        });
        btEdit.setOnAction((event2)->{
            if(txt4UpdateValid()){
                if(AuxAlgorithm.validDate(txtFechaCompra.getText())){
                    update();
                    emptyTxts();
                    fillListView();
                    fillTxts();                         
                }else{
                    dateNotValid();
               
                }
            }
        });
        
        btDelete.setOnAction((event3)->{
            if(!txtIDCompra.getText().isBlank() && IDCompraAvailable()){
                deletePurchase();
                fillListView();
                emptyTxts();
                fillTxts();
            }
        });
        
        
        
    }
    private ObservableList<Compra> getPurchases(){
        ObservableList<Compra> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM COMPRA";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Compra purchase = createPurchase(rs);
                purchase.setIDMembresia(rs.getString("IDMembresia"));
                purchase.setIDPaquete(rs.getString("IDPaquete"));
                list.add(purchase);
            }
        }catch(SQLException sql){
            sql.printStackTrace();
        }
        return list;
    }
    private void fillListView(){
        ObservableList<Compra> list = getPurchases();
        colIDCompra.setCellValueFactory(new PropertyValueFactory<Compra,Integer>("IDCompra"));
        colFechaCompra.setCellValueFactory(new PropertyValueFactory<Compra, Date>("fechaCompra"));
        colRegalo.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("regalo"));
        colUsuarioID.setCellValueFactory(new PropertyValueFactory<Compra, String>("usuarioID"));
        colUsuarioID.setCellValueFactory(new PropertyValueFactory<Compra, String>("usuarioID"));
        colIDMembresia.setCellValueFactory(new PropertyValueFactory<Compra,String>("IDMembresia"));
        colIDPaquete.setCellValueFactory(new PropertyValueFactory<Compra,String>("IDPaquete"));
        colCupon.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("cupon"));        
        colCantidadDeCuentos.setCellValueFactory(new PropertyValueFactory<Compra, Integer>("numeroCuentos"));        
        tableView.setItems(list);
    }
    private Compra createPurchase(ResultSet rs){
        if(rs != null){
            try{
                return new Compra(rs.getInt("IDCompra"),rs.getDate("fechaCompra"),rs.getInt("regalo"),rs.getString("UsuarioID"),rs.getInt("cupon"),rs.getInt("NumCuentos"));
            }catch(SQLException sql){
                sql.printStackTrace();
            }
        }
        return null;
    }
    private void setAdminMenu(){
        btAdminMenu.setOnAction((event->{
            try{
                App.setRoot("adminMenu");
            }catch(Exception e){
                e.printStackTrace();
            }
        }));
    }

    
    private void addUser(){
        String query = "INSERT INTO COMPRA VALUES(?,?,?,?,?,?,?,?)";
        
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
            Date date = java.sql.Date.valueOf(txtFechaCompra.getText());
            statement.setInt(1, Integer.valueOf(txtIDCompra.getText()));
            statement.setDate(2, date);
            statement.setInt(3,Integer.valueOf(txtRegalo.getText()));
            statement.setString(4,txtUsuarioID.getText());
            if(AuxAlgorithm.IDAlreadyInBDPackages(txtIDPaquete.getText())){
                statement.setString(5, txtIDPaquete.getText());
                statement.setNull(6, java.sql.Types.VARCHAR);
            }
            if(AuxAlgorithm.isMembresia(txtIDMembresia.getText())){
                statement.setString(6,txtIDMembresia.getText());
                statement.setNull(5, java.sql.Types.VARCHAR);
            }
            
            statement.setInt(7, Integer.valueOf(txtCupon.getText()));
            statement.setInt(8, Integer.valueOf(txtCantidadDeCuentos.getText()));
            statement.execute();
        }catch(SQLException sql){
            sql.printStackTrace();
        }
    }
    private boolean txtsValid(){
        if(txtIDCompra.getText().isBlank() || txtFechaCompra.getText().isBlank() || txtRegalo.getText().isBlank() ||txtUsuarioID.getText().isBlank()||txtCupon.getText().isBlank() ||txtCantidadDeCuentos.getText().isBlank()){
            AlertTxtsEmpty();
            return false;
        }
        return true;
    }
    private void AlertTxtsEmpty(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. UNO O MUCHOS DE LOS TEXTFIELDS ESTÁN VACIOS");
        alert.showAndWait();
    }
    private boolean ValidInsertion(){
        return txtsValid() && IDCompraExists4Insertion() && DateValid() 
                && IDUserExists()&&CuponValid() && regaloValid()&& ValidNumCuentos()  ;
    }
    private boolean IDUserExists(){
        if(AuxAlgorithm.IDAlreadyInDBUsers(txtUsuarioID.getText())){
            return true;
        }else{
            UserNotFound();
            return false;
        }
    }
    private boolean IDCompraAvailable(){
        return AuxAlgorithm.IDAlreadyInDBPurchases(Integer.valueOf(txtIDCompra.getText()));
    }
    private boolean IDCompraExists4Insertion(){
        if(Integer.valueOf(txtIDCompra.getText())<0){
            NegativeNumberAlert();
            return false;
        }
        if(AuxAlgorithm.IDAlreadyInDBPurchases(Integer.valueOf(txtIDCompra.getText()))){
            IDPurchaseExistentAlert();
            return false;
        }
        return true;
    }
    private boolean IDMembresiaExists(){
        if( AuxAlgorithm.isMembresia(txtIDMembresia.getText())){
            return true;
        }
        MembresiaNotExistent();
        return false;
    }
    private boolean IDPaqueteExists(){
        if(AuxAlgorithm.IDAlreadyInBDPackages(txtIDPaquete.getText())){
            return true;
        }else{
            PaqueteNotExistent();
            return false;
        }
    }
    private boolean DateValid(){
        if(AuxAlgorithm.validDate(txtFechaCompra.getText())){
            return true;
        }else{
            dateNotValid();
            return false;
        }
    }
    private boolean CuponValid(){
        if(AuxAlgorithm.isCuponValid(Integer.valueOf(txtCupon.getText()))){
            return true;
        }else{
            AlertCupon();
            return false;
        }
    }
    private boolean regaloValid(){
        if(AuxAlgorithm.isRegaloValid(Integer.valueOf(txtRegalo.getText()))){
            return true;
        }else{
            AlertRegalo();
            return false;
        }
    }
    private boolean ValidNumCuentos(){
        if(AuxAlgorithm.NumCuentosValid(Integer.valueOf(txtCantidadDeCuentos.getText()))){
            return true;
        }else{
            AlertNumeroCuentos();
            return false;
        }
    }
    
    
 
    private void UserNotFound(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. USUARIO NO EXISTE");
        alert.showAndWait();
    }
    private void NegativeNumberAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. NO SE ADMITEN VALORES NEGATIVOS");
        alert.showAndWait();
    }
    private void dateNotValid(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. FORMATO DE FECHA NO VALIDO");
        alert.showAndWait();
    }
    private void MembresiaNotExistent(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. NO EXISTE ESTA MEMBRESIA");
        alert.showAndWait();
    }
    private void PaqueteNotExistent(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. NO EXISTE PAQUETE");
        alert.showAndWait();
    }
    private void IDPurchaseExistentAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. IDCOMPRA YA EXISTENTE");
        alert.showAndWait();
    }
    private void IDCompraNotExistent(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. IDCOMPRA NO EXISTE");
        alert.showAndWait();
    }
    private void AlertCupon(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. CAMPO CUPON SOLO ACEPTA 0 Y 1");
        alert.showAndWait();
    }
    private void AlertRegalo(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText( "ERROR. CAMPO DE REGALO SOLO ACEPTA 0 Y 1");
        alert.showAndWait();
    }
    private void AlertNumeroCuentos(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("ERROR. NUMERO DE CUENTOS NO VALIDO");
        alert.showAndWait();
    }
    
    private boolean validMembresiaNPaquete(){
        if(AuxAlgorithm.isMembresia(txtIDMembresia.getText()) && AuxAlgorithm.IDAlreadyInBDPackages(txtIDPaquete.getText()) ){
            return false;
        }else if(!AuxAlgorithm.isMembresia(txtIDMembresia.getText()) && !AuxAlgorithm.IDAlreadyInBDPackages(txtIDPaquete.getText())){
            return false;
        }else if(AuxAlgorithm.isMembresia(txtIDMembresia.getText()) && !AuxAlgorithm.IDAlreadyInBDPackages(txtIDPaquete.getText())){
            return true;
        }else if(!AuxAlgorithm.isMembresia(txtIDMembresia.getText()) && AuxAlgorithm.IDAlreadyInBDPackages(txtIDPaquete.getText())){
            return true;
        }else{
            return false;
        }
        
    }
    private void fillTxts(){
        txtIDPaquete.setText("nulo");
        txtIDMembresia.setText("nulo");
    }
    private void emptyTxts(){
        txtIDCompra.clear();
        txtFechaCompra.clear();
        txtRegalo.clear();
        txtUsuarioID.clear();
        txtCupon.clear();
        txtCantidadDeCuentos.clear();       
    }
    public void deletePurchase(){
        String query = "DELETE FROM COMPRA WHERE IDCompra = ?";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)){
            statement.setInt(1, Integer.valueOf(txtIDCompra.getText()));
            statement.execute();
        }catch(SQLException sql){
            sql.printStackTrace();
        }
    }

    @FXML
    private void MouseClickedHandler(MouseEvent event) {
        Compra purchase = tableView.getSelectionModel().getSelectedItem();
        txtIDCompra.setText(String.valueOf(purchase.getIDCompra()));
        txtFechaCompra.setText(String.valueOf(purchase.getFechaCompra()));
        txtRegalo.setText(String.valueOf(purchase.getRegalo()));
        txtUsuarioID.setText(String.valueOf(purchase.getUsuarioID()));
        txtCupon.setText(String.valueOf(purchase.getCupon()));
        txtCantidadDeCuentos.setText(String.valueOf(purchase.getNumeroCuentos()));
        txtIDMembresia.setText(String.valueOf(purchase.getIDMembresia()));
        txtIDPaquete.setText(String.valueOf(purchase.getIDPaquete()));
        
        
    }
    private void update(){
        String query = "UPDATE COMPRA SET fechaCompra = ?, cupon = ?, regalo = ? where IDCompra = ?";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareCall(query)){
            statement.setString(1, txtFechaCompra.getText());
            statement.setString(2, txtCupon.getText());
            statement.setString(3, txtRegalo.getText());
            statement.setString(4, txtIDCompra.getText());
            statement.execute();
        }catch(SQLException sql){
            sql.printStackTrace();
        }
    }
    private boolean txt4UpdateValid(){
        if( txtFechaCompra.getText().isBlank() || txtCupon.getText().isBlank() ||
                txtRegalo.getText().isBlank() || txtIDCompra.getText().isBlank()){
            AlertTxtsEmpty();    
            return false;
        }
        if(IDCompraAvailable()){
            if(CuponValid() && regaloValid()){
                return true;
            }else{
                return false;
            }
        }else{
            IDCompraNotExistent();
            return false;
        }
    }       
}

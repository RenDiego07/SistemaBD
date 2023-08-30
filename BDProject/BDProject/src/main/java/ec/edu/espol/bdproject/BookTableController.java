/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package ec.edu.espol.bdproject;

import ec.edu.espol.bd.Conexion;
import ec.edu.espol.bd.Libro;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
/**
 * FXML Controller class
 *
 * @author diego
 */
public class BookTableController implements Initializable {

    @FXML
    private ComboBox viewsCB;
    @FXML
    private TextField txtIDLibro;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtDescripcion;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtCategoria;
    @FXML
    private TextField txtAutor;
    @FXML
    private TextField txtEditorial;
    @FXML
    private TextField txtIdioma;
    @FXML
    private TextField txtPrecio;
    @FXML
    private TableView<Libro> tableView;
    @FXML
    private TableColumn<Libro, String> colIDLibro;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, String> colDescripcion;
    @FXML
    private TableColumn<Libro, Integer> colStock;
    @FXML
    private TableColumn<Libro, String> colCategoria;
    @FXML
    private TableColumn<Libro, String> colAutor;
    @FXML
    private TableColumn<Libro, String> colEditorial;
    @FXML
    private TableColumn<Libro, String> colIdioma;
    @FXML
    private TableColumn<Libro, Float> colPrecio;
    @FXML
    private Button btInsert;
    @FXML
    private Button btEdit;
    @FXML
    private Button btDelete;
    @FXML
    private Button btAdminMenu;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillListView();
        setUpViewsCB();
        setHandlers();        
    }    
    private ObservableList<Libro> getLibros() {
        ObservableList<Libro> list = FXCollections.observableArrayList();
        String query = "Select * from Libro";
        try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                list.add(createLibro(rs));
            }
        }catch(SQLException sql) {
            sql.printStackTrace();
        }
        return list;
    }
    
    private Libro createLibro(ResultSet rs) {
        if(rs != null) {
            try {
                return new Libro(rs.getString("IDLibro"), rs.getString("titulo"), rs.getString("descripcion"), 
                       rs.getInt("stock"), rs.getString("categoria"), rs.getString("autor"), rs.getString("editorial"),
                       rs.getString("idioma"), rs.getFloat("precio"));               
            }catch(SQLException sql) {
                sql.printStackTrace();
            }
        }
        return null;
    }
    
    private void fillListView(){
        ObservableList<Libro> list = getLibros();
        colIDLibro.setCellValueFactory(new PropertyValueFactory<Libro, String>("idLibro"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Libro, String>("descripcion"));
        colStock.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("stock"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Libro, String>("categoria"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
        colIdioma.setCellValueFactory(new PropertyValueFactory<Libro, String>("idioma"));        
        colPrecio.setCellValueFactory(new PropertyValueFactory<Libro, Float>("precio"));
        
        tableView.setItems(list);
    }
        
    @FXML
    private void fillTextFields(MouseEvent event) {
        Libro l = tableView.getSelectionModel().getSelectedItem();
        if(l != null) {
            txtIDLibro.setText(l.getIdLibro());
            txtTitulo.setText(l.getTitulo());
            txtDescripcion.setText(l.getDescripcion());
            txtStock.setText(l.getStock()+"");
            txtCategoria.setText(l.getCategoria());
            txtAutor.setText(l.getAutor());
            txtEditorial.setText(l.getEditorial());            
            txtIdioma.setText(l.getIdioma());            
            txtPrecio.setText(l.getPrecio()+"");            
        }    
    }
    private void setHandlers() {
        btInsert.setOnMouseClicked(eh -> {
            if(!missingFields()) {
                insertBook();
                fillListView();
            }else {
                missingFieldsAlert();
            }
        });
        btDelete.setOnMouseClicked(eh -> {
            if(!missingFields()) {
                deleteBook();
                fillListView();
            }else {
                notSelectedRecord();
            }
        });
        btEdit.setOnMouseClicked(eh -> {
            if(!missingFields()) {
                updateBook();
                fillListView();
            }else {
                notSelectedRecord();
            }
        });
        btAdminMenu.setOnMouseClicked(eh -> {
            try {
                App.setRoot("adminMenu");
            }catch(Exception e) {
                e.printStackTrace();
            }
        });
    }
    
    private boolean missingFields() {
        return txtIDLibro.getText().isBlank() || txtTitulo.getText().isBlank() || txtDescripcion.getText().isBlank() || txtStock.getText().isBlank() || txtCategoria.getText().isBlank() || txtAutor.getText().isBlank() || txtEditorial.getText().isBlank() || txtIdioma.getText().isBlank() || txtPrecio.getText().isBlank();
    }
    
    private void insertBook(){
        String query = "{call insertBook(?,?,?,?,?,?,?,?,?)}";
        try(CallableStatement statement = Conexion.getInstance().conectar().prepareCall(query) ){
            statement.setString(1, txtIDLibro.getText());
            statement.setString(2, txtTitulo.getText());
            statement.setString(3, txtDescripcion.getText());
            statement.setInt(4, Integer.parseInt(txtStock.getText()));
            statement.setString(5, txtCategoria.getText());
            statement.setString(6, txtAutor.getText());
            statement.setString(7, txtEditorial.getText());
            statement.setString(8, txtIdioma.getText());
            statement.setFloat(9, Float.parseFloat(txtPrecio.getText()));
            
            statement.execute();
        }catch(SQLIntegrityConstraintViolationException icve) {
            existingBook();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    private void deleteBook() {
        String query = "{call deleteBook(?)}";
        try(CallableStatement statement = Conexion.getInstance().conectar().prepareCall(query)) {
            statement.setString(1, txtIDLibro.getText());
            statement.execute();
        }catch(SQLException sql) {
            sql.printStackTrace();
        }
    }
    
    private void updateBook() {
        String query = "{call updateBook(?,?,?,?,?)}";
        try(CallableStatement statement = Conexion.getInstance().conectar().prepareCall(query)) {
            statement.setString(1, txtIDLibro.getText());
            statement.setString(2, txtDescripcion.getText());
            statement.setInt(3, Integer.parseInt(txtStock.getText()));
            statement.setString(4, txtCategoria.getText());
            statement.setFloat(5, Float.parseFloat(txtPrecio.getText()));
            
            statement.execute();
        }catch(SQLException sql) {
            sql.printStackTrace();
        }
    }
    
    private void missingFieldsAlert() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText("Missing fields found");
        a.setContentText("Fill all the textfields");
        a.showAndWait();
    }
    
    private void notSelectedRecord() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText("Not selected record");
        a.setContentText("Select a record for deleting");
        a.showAndWait();        
    }
    
    private void existingBook() {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText("Existing book");
        a.setContentText("The book already exists in the database");
        a.showAndWait();         
    }    
    
    private void setUpViewsCB() {
        viewsCB.getItems().add("Libros en stock");
        viewsCB.getItems().add("Todos los libros");
        viewsCB.setOnAction(eh -> {
            String selected = (String) viewsCB.getSelectionModel().getSelectedItem();
            ObservableList<Libro> books = null;
            switch(selected) {
                case "Libros en stock":
                    books = getBooksFromQuery("SELECT * FROM librosEnStock;");
                    break;
                case "Todos los libros":
                    books = getBooksFromQuery("SELECT * FROM libro");
                    break;
            }
            if(tableView.getItems() != null) {
                tableView.getItems().clear();            
            }
            tableView.setItems(books);
        });
    }
    
    private ObservableList<Libro> getBooksFromQuery(String query) {
          ObservableList<Libro> list = FXCollections.observableArrayList();
          try(PreparedStatement statement = Conexion.getInstance().conectar().prepareStatement(query)) {
                ResultSet rs = statement.executeQuery();
                while(rs.next()) {
                    Libro book = createLibro(rs);
                    list.add(book);
                }
            }catch(SQLException sql) {
                sql.printStackTrace();
            }     
        return list;
    }
}

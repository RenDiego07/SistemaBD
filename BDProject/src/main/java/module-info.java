module ec.edu.espol.bdproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens ec.edu.espol.bdproject to javafx.fxml;
   
    exports ec.edu.espol.bdproject;
}

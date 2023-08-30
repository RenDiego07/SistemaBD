module ec.edu.espol.bdproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    opens ec.edu.espol.bdproject to javafx.fxml;
    opens ec.edu.espol.bd;
    exports ec.edu.espol.bdproject;
}

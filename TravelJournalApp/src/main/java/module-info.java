module com.example.signupmysql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.phase4 to javafx.fxml;
    exports com.example.phase4;
}
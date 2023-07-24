module com.example.signupmysql {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.signupmysql to javafx.fxml;
    exports com.example.signupmysql;
}
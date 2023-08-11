module com.example.fruitsapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires GNAvatarView;
    requires org.xerial.sqlitejdbc;


    opens com.example.fruitsapp to javafx.fxml;
    exports com.example.fruitsapp;
}
module com.example.fruitsapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fruitsapp to javafx.fxml;
    exports com.example.fruitsapp;
}
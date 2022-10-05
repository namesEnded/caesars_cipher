module com.example.labOne {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.labOne to javafx.fxml;
    exports com.example.labOne;
}
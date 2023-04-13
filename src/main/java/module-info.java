module com.example.csfinal {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.csfinal to javafx.fxml;
    exports com.example.csfinal;
}
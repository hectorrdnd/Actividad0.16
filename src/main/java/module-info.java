module com.empresa.actividad016 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.empresa.actividad016 to javafx.fxml;
    exports com.empresa.actividad016;
}
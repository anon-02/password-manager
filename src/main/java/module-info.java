module com.example.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;

    opens com.example.passwordmanager to javafx.fxml;
    exports com.example.passwordmanager;
}
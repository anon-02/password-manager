module com.example.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.passwordmanager to javafx.fxml;
    exports com.example.passwordmanager;
    exports com.example.passwordmanager.Controllers;
    opens com.example.passwordmanager.Controllers to javafx.fxml;
}
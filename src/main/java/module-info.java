module com.example.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires java.sql;

    opens com.example.passwordmanager to javafx.fxml;
    exports com.example.passwordmanager;
    exports com.example.passwordmanager.Model;
    opens com.example.passwordmanager.Model to javafx.fxml;
}
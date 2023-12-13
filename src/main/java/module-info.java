module com.example.passwordmanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires javafx.graphics;
    requires java.sql;
    requires com.google.common;
    requires java.management;

    opens com.example.passwordmanager to javafx.fxml;
    exports com.example.passwordmanager;
    exports com.example.passwordmanager.Model;
    opens com.example.passwordmanager.Model to javafx.fxml;
    exports com.example.passwordmanager.Model.dbStuff;
    opens com.example.passwordmanager.Model.dbStuff to javafx.fxml;
    exports com.example.passwordmanager.ViewManager;
    opens com.example.passwordmanager.ViewManager to javafx.fxml;
}
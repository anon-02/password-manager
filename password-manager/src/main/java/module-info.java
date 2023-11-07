module com.example.passwordmanager {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive java.sql;
    

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.passwordmanager to javafx.fxml;
    exports com.example.passwordmanager.Controllers;
}
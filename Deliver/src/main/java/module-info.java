module com.example.deliver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires java.datatransfer;
    requires json.simple;


    opens com.example.deliver to javafx.fxml;
    exports com.example.deliver;
}

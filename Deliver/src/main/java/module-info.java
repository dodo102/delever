module com.example.deliver {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.deliver to javafx.fxml;
    exports com.example.deliver;
}
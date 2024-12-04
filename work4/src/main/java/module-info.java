module org.example.work4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens org.example.work4 to javafx.fxml;
    exports org.example.work4;
}
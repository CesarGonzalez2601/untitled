module sv.com.pruebanodospacientes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens sv.com.pruebanodospacientes to javafx.fxml;
    exports sv.com.pruebanodospacientes;
    exports sv.com.pruebanodospacientes.controller;
    opens sv.com.pruebanodospacientes.controller to javafx.fxml;
    exports sv.com.pruebanodospacientes.modelo;
    opens sv.com.pruebanodospacientes.modelo to javafx.fxml;
}
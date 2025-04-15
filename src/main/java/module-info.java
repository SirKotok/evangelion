module eva.evangelion {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
  //  requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens eva.evangelion to javafx.fxml;
    exports eva.evangelion;
    exports eva.evangelion.gameboard;
    opens eva.evangelion.gameboard to javafx.fxml;
}
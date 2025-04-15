package eva.evangelion.view.evainterface;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class EvaLabel  extends Label {




    public EvaLabel(String text) {

     //   setPrefWidth(380);
     //   setPrefHeight(49);
        setText(text);
        setWrapText(true);;
        setAlignment(Pos.CENTER);

    }

    public void SetPosition(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }



}

package eva.evangelion.view.evainterface;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class EvaButton extends Button {


    public float realX;
    public float realY;
    public boolean hidden = false;

    public EvaButton(String text) {
        setText(text);
        setPrefWidth(90);
        setPrefHeight(90);

    }

    public void Explain(String tip) {
        Tooltip tooltip = new Tooltip(tip);
        tooltip.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-background-color: linear-gradient(to bottom, #2D5F8A, #1E4160);" +
                        "-fx-background-radius: 4;" +
                        "-fx-padding: 8px;" +
                        "-fx-border-color: #FFFFFF;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 4;"
        );
       this.setTooltip(tooltip);
    }


    public void setPosition(float x, float y){
        this.setLayoutY(y);
        this.setLayoutX(x);
        realX = x;
        realY = y;

    }
    public void setLocation(float x, float y){
        this.setLayoutY(y);
        this.setLayoutX(x);
    }

    public void hideButton() {
        setLocation(10000, 10000);
        hidden = true;
    }
    public void returnToLocation() {
        setLocation(realX, realY);
        hidden = false;
    }
    public boolean isButtonHidden() {
        return hidden;
    }


}

package eva.evangelion.view.evainterface;

import javafx.scene.control.Button;

public class EvaButton extends Button {


    public EvaButton(String text) {
        setText(text);
      //  setButtonFont();
         setPrefWidth(90);
         setPrefHeight(90);
     //   setStyle(BUTTON_FREE_STYLE);
     //   initializeButtonListeners();

    }


    public void setPosition(float x, float y){
        this.setLayoutY(y);
        this.setLayoutX(x);
    }


  /*  private void setButtonFont() {

        setFont(Font.loadFont(getClass().getResourceAsStream(FONT_PATH), 23));

    }

    private void setButtonPressedStyle() {
        setStyle(BUTTON_PRESSED_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);

    }

    private void setButtonReleasedStyle() {
        setStyle(BUTTON_FREE_STYLE);
        setPrefHeight(45);
        setLayoutY(getLayoutY() - 4);

    }


    private void initializeButtonListeners() {

        setOnMousePressed(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonPressedStyle();
                }

            }
        });

        setOnMouseReleased(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)) {
                    setButtonReleasedStyle();
                }

            }
        });

        setOnMouseEntered(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());

            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setEffect(null);

            }
        });












    }  */

}

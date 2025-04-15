package eva.evangelion.view.evainterface;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;

public class DisplaySubSpace extends EvaMenuSubScene{




    public DisplaySubSpace() {
        super(new AnchorPane(), 370, 300);
      //  prefWidth(200);
      //  prefHeight(200);

        distance = 320f;
        isHidden = true ;

        setLayoutX(10900);
        setLayoutY(0);


    }

    public DisplaySubSpace(int distance, int y, int width, int height) {
        super(new AnchorPane(), width, height);

        this.distance = distance;
        isHidden = true ;

        setLayoutX(10900);
        setLayoutY(y);


    }

    public DisplaySubSpace(int distance, int y, int width, int height, int battlefielddelta) {
        super(new AnchorPane(), width, height);

        this.distance = 155+battlefielddelta;
        isHidden = true ;

        setLayoutX(10900);
        setLayoutY(y);


    }




    public void moveSubScene() {


        if (isHidden) {
           this.setLayoutX(distance);
            isHidden = false;
        } else {
            this.setLayoutX(10000);
            isHidden = true ;
        }


    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }


}

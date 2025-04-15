package eva.evangelion.view.evainterface;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;

public class EvaMenuSubScene extends SubScene{



    public  boolean isHidden;
    protected  float distance = 110f;
    private float distanceY;
    private int abslevel;

    public EvaMenuSubScene(int level) {
        super(new AnchorPane(), 100, 1000);
        prefWidth(100);
        prefHeight(1000);
        this.distance = 90+distance*level;
        this.abslevel = level;
        this.distanceY = 100;

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(null);

        isHidden = true ;

        setLayoutX(10900);
        setLayoutY(100);


    }

    public EvaMenuSubScene(int level, boolean b, int x, int y) {
        super(new AnchorPane(), x, y);
        prefWidth(x);
        prefHeight(y);
        this.distance = 90+distance*level;
        this.abslevel = level;
        this.distanceY = 100;

        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(null);

        isHidden = true ;

        setLayoutX(10900);
        setLayoutY(100);


    }
    
    
    public EvaMenuSubScene(int level, int y) {
        super(new AnchorPane(), 100, 500);
        prefWidth(100);
        prefHeight(500);
        this.distance = 100+distance*level;
        this.abslevel = level;
        this.distanceY = y;


        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(null);

        isHidden = true ;

        setLayoutX(10900);
        setLayoutY(100);


    }


    public EvaMenuSubScene(int level, int y, int fielddelta) {
        super(new AnchorPane(), 100, 500);
        prefWidth(100);
        prefHeight(500);
        this.distance = 150+(level-4)*110+fielddelta;
        this.abslevel = level;
        this.distanceY = y;


        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(null);

        isHidden = true ;

        setLayoutX(10900);
        setLayoutY(100);


    }
    public EvaMenuSubScene(int level, int y, int fielddelta, int width) {
        super(new AnchorPane(), width, 500);
        prefWidth(width);
        prefHeight(500);
        this.distance = 150+(level-4)*110+fielddelta;
        this.abslevel = level;
        this.distanceY = y;


        AnchorPane root2 = (AnchorPane) this.getRoot();
        root2.setBackground(null);

        isHidden = true ;

        setLayoutX(10900);
        setLayoutY(100);


    }





    public EvaMenuSubScene(AnchorPane pane, int i, int j) {
        super(pane, i, j);
    }





    public int getLevel(){
        return this.abslevel;
    }


    public void moveSubScene() {


        if (isHidden) {

           this.setLayoutX(distance);
           this.setLayoutY(distanceY);
            isHidden = false;

        } else {

            this.setLayoutX(10000);
            this.setLayoutY(distanceY);
            isHidden = true ;
        }


    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }


}

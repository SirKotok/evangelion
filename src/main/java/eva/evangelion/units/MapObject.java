package eva.evangelion.units;

import javafx.scene.shape.Circle;

public class MapObject {
    int X;
    int Y;
    public Circle ItemCircle;




    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }
    public void setX(int x) {
        X = x;
    }
    public void setY(int y) {
       Y = y;
    }


    public void setPosition(int x, int y) {
        setX(x);
        setY(y);
    }

}

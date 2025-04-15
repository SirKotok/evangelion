package eva.evangelion.view.shape;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Arrow extends Line {

    private Polygon triangle;
    private Pane Parent;


    public Arrow(Pane Parent) {
        super(0, 2, 3, 5);
        this.Parent = Parent;
        Parent.getChildren().addAll(this);
        triangle = new Polygon(getEndX(), getEndY(), getEndX() - 16, getEndY() + 8, getEndX() - 16, getEndY() - 8);
        Parent.getChildren().add(triangle);
        canvas(3, 5);

    }

    public void SetColor(Color color) {
        this.setStroke(color);
        triangle.setFill(color);
    }


    private DoubleBinding dx;
    private DoubleBinding dy;

    public double StartX;
    public double StartY;
    public double EndX;
    public double EndY;

    public void redrawArrow(double startX, double startY, double endX, double endY){
        setStartX(startX);
        setStartY(startY);
        setEndX(endX);
        setEndY(endY);
        triangle.setRotate(0);
        canvas(endX+5, endY-5);
    }
    public void setStartEnd(double startX, double startY, double endX, double endY){
        StartX = startX;
        StartY = startY;
        EndX = endX;
        EndY = endY;
    }

    public void UpdateArrow(double x, double y) {
        redrawArrow(StartX - x, StartY - y, EndX - x, EndY - y);
    }

    public void canvas(double x, double y){
        dx = endXProperty().add(startXProperty().negate());
        dy = endYProperty().add(startYProperty().negate());
        updateTriangleRotation();
        triangle.setLayoutY(y);
        triangle.setLayoutX(x);
    }
    private void updateTriangleRotation() {
        double angle = Math.toDegrees(Math.atan2(
                getEndY() - getStartY(),
                getEndX() - getStartX()
        ));
        triangle.setRotate(angle);
    }


    private double getAngle(double dy ,double dx){
        return Math.toDegrees(Math.atan2(dy, dx));
    }

}
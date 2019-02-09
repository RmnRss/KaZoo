package Model.Class.Zoo.Entity;

import java.io.Serializable;

/***
 * Class of an obstacle
 */
public class Obstacle implements Serializable {
    private Position position;
    private double width;
    private double height;

    /***
     * Constructor taking position and size on the canvas
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public Obstacle(double x, double y, double width, double height) {
        position = new Position(x, y);
        this.width = width;
        this.height = height;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

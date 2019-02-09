package Model.Class.Zoo.Entity;

import java.io.Serializable;

/***
 * Position of an entity in the game (on the canvas)
 */
public class Position implements Serializable {
    private double positionX;
    private double positionY;

    public Position(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getX() {
        return positionX;
    }

    public void setX(double positionX) {
        this.positionX = positionX;
    }

    public double getY() {
        return positionY;
    }

    public void setY(double positionY) {
        this.positionY = positionY;
    }

    public void setPosition(double x, double y) {
        positionX = x;
        positionY = y;
    }
}

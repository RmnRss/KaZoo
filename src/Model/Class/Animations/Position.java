package Model.Class.Animations;

import java.io.Serializable;

/***
 * Position of an entity in the game (on the canvas)
 */
public class Position implements Serializable {
    private int positionX;
    private int positionY;

    public Position(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getX() {
        return positionX;
    }

    public void setX(int positionX) {
        this.positionX = positionX;
    }

    public int getY() {
        return positionY;
    }

    public void setY(int positionY) {
        this.positionY = positionY;
    }

    public void setPosition(int x, int y)
    {
        positionX = x;
        positionY = y;
    }
}

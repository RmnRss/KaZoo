package Model.Class.Zoo;

import Model.Class.Animations.Position;
import javafx.geometry.Rectangle2D;

import java.io.Serializable;

public class Obstacle implements Serializable
{
    private Position position;
    private int width;
    private int height;

    public Obstacle(int x, int y, int width, int height) {
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

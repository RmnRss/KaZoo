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

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(position.getX(), position.getY(), width, height);
    }


}

package Model.Class.Zoo;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class Obstacle
{
    private int size;
    private Point2D position;

    private Image sprite;

    public Obstacle() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Image getSprite() {
        return sprite;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }
}

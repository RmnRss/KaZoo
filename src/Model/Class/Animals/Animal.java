package Model.Class.Animals;

import Model.Class.Client;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Random;

public abstract class Animal
{
    protected int speed;
    protected int name;

    protected int age;

    protected Client owner;

    protected String sex;
    protected float averageSize;
    protected float size;

    protected int litter;

    protected Animal father;
    protected Animal mother;

    protected Image sprite;

    protected Point2D position;

    /***
     * Function
     * Handles the growth of an Animal
     * Changing size and sprites depending on its age
     * ***/
    abstract void growth(int Age);

    public int getTargetX()
    {
        int mapSide = 500;

        Random r = new Random();

        return r.nextInt(mapSide);
    }

    public int getTargetY()
    {
        int mapSide = 500;

        Random r = new Random();

        return r.nextInt(mapSide);
    }

    public void move()
    {
        int targetX = getTargetX();
        int targetY = getTargetY();



    }
}

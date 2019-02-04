package Model.Class.Zoo.Animals;

import Model.Class.Animations.Position;
import Model.Class.Animations.Velocity;
import Model.Class.Client;
import javafx.geometry.Rectangle2D;

import java.util.HashMap;
import java.util.Random;

/***
 * Implementation of Animal for penguins
 */
public class Penguin extends Animal
{
    /***
     * Empty constructor
     */
    public Penguin()
    {

    }

    /***
     * Constructor using a Name and a Sex
     * @param Name
     * @param Sex
     */
    public Penguin(String Name, String Sex, String Owner)
    {
        babies = new HashMap<>();
        name = Name;
        sex = Sex;
        owner = Owner;
        averageSize = 5;
        litter = 1;
        age = 5;
        canHaveBabies = isAnAdult();

        Random r = new Random();
        int mapSide = 500;

        position = new Position(r.nextInt(mapSide), r.nextInt(mapSide));
        velocity = new Velocity(5);
        target = new Position(r.nextInt(mapSide), r.nextInt(mapSide));
    }

    /***
     * Complete constructor for baby penguins
     * @param Name
     * @param Sex
     * @param Father
     * @param Mother
     */
    public Penguin(String Name, String Sex, String Owner, Animal Father, Animal Mother)
    {
        this(Name, Sex, Owner);
        size = 1;
        father = Father;
        mother = Mother;
        age = 0;
        canHaveBabies = isAnAdult();
    }

    /***
     * Implementation for the growth of a penguin
     * @param Age
     */
    @Override
    public void growth(int Age)
    {
        Random r = new Random();
        switch (Age)
        {
            //Baby
            case 0:
                //sprite = ;
                break;
            //Child
            case 2:
                size = size + r.nextInt(3-(int) size);
                //sprite = ;
                break;
            //Adult
            case 5:
                size = size + r.nextInt((int)((averageSize+1)-size));
                //sprite = ;
                break;
            //Old
            case 15:
                //sprite = ;
                break;
            //Ded
            case 20:
                //sprite = ;
                break;
        }
    }

    @Override
    public String toString(){
        return "Penguin";
    }


}

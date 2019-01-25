package Model.Class.Zoo.Animals;

import Model.Class.Animations.Position;
import Model.Class.Animations.Velocity;
import javafx.geometry.Rectangle2D;

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
    public Penguin(String Name, String Sex)
    {
        name = Name;
        sex = Sex;
        averageSize = 5;
        litter = 1;

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
    public Penguin(String Name, String Sex, Animal Father, Animal Mother)
    {
        this(Name, Sex);
        size = 1;
        father = Father;
        mother = Mother;
        age = 0;
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


}

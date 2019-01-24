package Model.Class.Zoo.Animals;

import Model.Class.Animations.Position;
import Model.Class.Animations.Velocity;
import javafx.geometry.Rectangle2D;

import java.util.Random;

public class Bear extends Animal
{
    /***
     * Empty Bear constructor
     */
    public Bear()
    {

    }

    /***
     * Classic constructor for a normal bear
     * @param Name
     * @param Sex
     */
    public Bear(String Name, String Sex)
    {
        averageSize = 15;
        litter = 2;

        Random r = new Random();
        int mapSide = 500;

        position = new Position(r.nextInt(mapSide), r.nextInt(mapSide));
        velocity = new Velocity(5);
        target = new Position(r.nextInt(mapSide), r.nextInt(mapSide));
    }

    /***
     * Constructor for a baby bear
     * @param Name
     * @param Sex
     * @param Father
     * @param Mother
     */
    public Bear(String Name, String Sex, Animal Father, Animal Mother)
    {
        this(Name, Sex);
        size = 5;
        father = Father;
        mother = Mother;
        age = 0;

    }

    /***
     * Implementation for the growth of a bear
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
                size = size + r.nextInt(8-(int) size);
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

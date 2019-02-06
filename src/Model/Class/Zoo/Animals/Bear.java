package Model.Class.Zoo.Animals;

import Model.Class.Animations.Position;
import Model.Class.Animations.Velocity;
import Model.Class.Client;
import Model.Class.Network.TempReproduction;
import Model.Class.Player;
import javafx.geometry.Rectangle2D;

import java.nio.charset.Charset;
import java.util.HashMap;
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
    public Bear(String Name, String Sex, Player Owner, String status)
    {
        babies = new HashMap<>();
        name = Name;
        sex = Sex;
        owner = Owner;
        size = 30;
        averageSize = 15;
        litter = 2;
        age = 5;
        canHaveBabies = isAnAdult();

        Random r = new Random();
        int mapSide = 500;

        position = new Position(r.nextInt(mapSide), r.nextInt(mapSide));
        velocity = new Velocity(5);
        target = new Position(r.nextInt(mapSide), r.nextInt(mapSide));

        imageUrl = "resources/img/triangle";

        if(status.equals("baby")){
            age = 0;
            canHaveBabies = isAnAdult();
            size = 15;
            velocity = new Velocity(4);
            imageUrl = "resources/img/btriangle";
        }
        
    }

    /***
     * Implementation for the growth of a bear
     * @param Age
     */
    @Override
    public synchronized void growth(int Age)
    {
        // TODO : Implement method

        Random r = new Random();
        switch (Age)
        {
            // Baby Stage
            case 0:
                break;
            // Child Stage
            case 2:
                size = size + r.nextInt(8-(int) size);
                break;
            // Adult Stage
            case 5:
                size = size + r.nextInt((int)((averageSize+1)-size));
                break;
            // Old Stage
            case 15:
                break;
            // Death
            case 20:
                break;
        }
    }

    @Override
    public String toString(){
        return "Bear";
    }

}

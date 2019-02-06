package Model.Class.Zoo.Animals;

import Model.Class.Zoo.Entity.Position;
import Model.Class.Zoo.Entity.Velocity;
import Model.Class.Zoo.Player;

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
    }

    @Override
    public String toString(){
        return "Bear";
    }

}

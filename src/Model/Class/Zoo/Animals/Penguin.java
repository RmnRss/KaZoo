package Model.Class.Zoo.Animals;

import Model.Class.Zoo.Entity.Position;
import Model.Class.Zoo.Entity.Velocity;
import Model.Class.Zoo.Player;

import java.util.HashMap;
import java.util.Random;

/***
 * Implementation of Animal for penguins
 */
public class Penguin extends Animal {
    /***
     * Empty constructor
     */
    public Penguin() {

    }

    /***
     * Constructor using a Name and a Sex
     * @param Name
     * @param Sex
     */
    public Penguin(String Name, String Sex, Player Owner, String status) {
        babies = new HashMap<>();
        name = Name;
        sex = Sex;
        owner = Owner;
        size = 30;
        averageSize = 5;
        litter = 1;
        age = 5;
        canHaveBabies = isAnAdult();

        Random r = new Random();
        int mapSide = 500;

        position = new Position(r.nextInt(mapSide), r.nextInt(mapSide));
        velocity = new Velocity(3);
        target = new Position(r.nextInt(mapSide), r.nextInt(mapSide));

        imageUrl = "resources/img/rectangle";

        if (status.equals("baby")) {
            age = 0;
            canHaveBabies = isAnAdult();
            size = 15;
            velocity = new Velocity(2);
            imageUrl = "resources/img/brectangle";
        }
    }


    /***
     * Implementation for the growth of a penguin
     * @param Age
     */
    @Override
    public synchronized void growth(int Age) {
        // TODO : Implement method
    }

    @Override
    public String toString() {
        return "Penguin";
    }
}

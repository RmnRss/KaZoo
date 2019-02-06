package Model.Class.Zoo.Animals;

import Model.Class.Animations.Position;
import Model.Class.Animations.Velocity;
import Model.Class.Client;
import Model.Class.Network.TempReproduction;
import javafx.geometry.Rectangle2D;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Random;

public class Turtle extends Animal
{
    /***
     * Empty constructor
     */
    public Turtle()
    {

    }

    /***
     * Adult Turtle constructor
     * @param Name
     * @param Sex
     */
    public Turtle(String Name, String Sex, String Owner)
    {
        babies = new HashMap<>();
        name = Name;
        sex = Sex;
        owner = Owner;
        size = 30;
        averageSize = 3;
        litter = 2;
        age = 5;
        canHaveBabies = isAnAdult();

        Random r = new Random();
        int mapSide = 500;

        position = new Position(r.nextInt(mapSide), r.nextInt(mapSide));
        velocity = new Velocity(1);
        target = new Position(r.nextInt(mapSide), r.nextInt(mapSide));

        imageUrl = "resources/img/circle";
    }

    /***
     * Baby Turtle constructor
     * @param Name
     * @param Sex
     * @param Father
     * @param Mother
     */
    public Turtle(String Name, String Sex, String Owner, Animal Father, Animal Mother)
    {
        this(Name, Sex, Owner);
        size = 15;
        father = Father;
        mother = Mother;
        age = 0;

        position = mother.getPosition();

        imageUrl = "resources/img/bcircle";
    }

    /***
     * Implementation for the growth of a turtle
     * @param Age
     */
    @Override
    public synchronized void growth(int Age)
    {
        Random r = new Random();

        switch (Age)
        {
            // Baby stage
            case 0:
                break;
            // Child stage
            case 2:
                size = size + r.nextInt(2-(int) size);
                break;
            // Adult stage
            case 5:
                size = size + r.nextInt((int)((averageSize+1)-size));
                break;
            // Old stage
            case 15:
                break;
            // Death
            case 20:
                break;
        }
    }

    @Override
    public String toString(){
        return "Turtle";
    }

    @Override
    public synchronized void haveBabiesWith(Animal otherAnimal) {
        // Initialization of reproduction rules and consequences
        byte[] array = new byte[10];
        new Random().nextBytes(array);
        String name = new String(array, Charset.forName("UTF-8"));
        String[] sexes = {"Male", "Female"};
        Random random = new Random();

        if(this.getCanHaveBabies()) {
            System.out.println("Creating baby...");
            if(this.getSex() == "Female") this.getBabies().put(name, new Turtle(name, sexes[random.nextInt(1)], this.getOwner(), otherAnimal, this));
            else this.getBabies().put(name, new Turtle(name, sexes[random.nextInt(1)], this.getOwner(), this, otherAnimal));
        }
        this.setCanHaveBabies(false);
        otherAnimal.setCanHaveBabies(false);
        this.setTarget();
        otherAnimal.setTarget();

        TempReproduction newTempReproduction = new TempReproduction(this, otherAnimal);
        Thread waitingReproduction = new Thread(newTempReproduction);
        waitingReproduction.start();
    }
}

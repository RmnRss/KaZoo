package Model.Class;

import Model.Class.Zoo.Animals.Animal;

import java.io.Serializable;
import java.util.HashMap;

/***
 * Player class
 * The color attribute represents the color chosen to display the animals owned by the player
 */
public class Player implements Serializable {

    private HashMap<String, Animal> ownedAnimals;
    private String name;
    private int color;

    /***
     * Constructor setting the name of the player
     * @param name
     */
    public Player(String name) {
        ownedAnimals = new HashMap<>();
        this.name = name;
    }

    public HashMap<String, Animal> getOwnedAnimals() {
        return ownedAnimals;
    }

    public synchronized void setOwnedAnimals(HashMap<String, Animal> newPlayerAnimals) {
        this.ownedAnimals = newPlayerAnimals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public synchronized void setColor(int color) {
        this.color = color;
    }
}

package Model.Class;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Zoo;

import java.io.Serializable;
import java.util.HashMap;

public class Player implements Serializable {
    private HashMap<String, Animal> playerAnimals;
    private String name;
    private int color;

    public Player(String name) {
        playerAnimals = new HashMap<>();
        this.name = name;
    }

    public HashMap<String, Animal> getPlayerAnimals() {
        return playerAnimals;
    }

    public void setPlayerAnimals(HashMap<String, Animal> newPlayerAnimals) {
        this.playerAnimals = newPlayerAnimals;
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

    public void setColor(int color) {
        this.color = color;
    }
}

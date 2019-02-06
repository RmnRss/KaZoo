package Model.Class;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Animals.Turtle;
import Model.Class.Zoo.Obstacle;
import Model.Class.Zoo.Zoo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/***
 * Player class
 * The color attribute represents the color chosen to displayAnimals the animals owned by the player
 */
public class Player implements Serializable
{
    private HashMap<String, Animal> playerAnimals = new HashMap<>();

    private String name;
    private int color;

    /***
     * Constructor setting the name of the player
     * @param name
     */
    public Player(String name) {
        this.name = name;
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

    public HashMap<String, Animal> getPlayerAnimals() {
        return playerAnimals;
    }

    public synchronized void setPlayerAnimals(HashMap<String, Animal> playerAnimals) {
        this.playerAnimals = playerAnimals;
    }

    /***
     * Adds a specific animal to the Zoo or overrides it
     * Only added if its owner is a connected player
     * Also add babies of said animals to the list of the zoo
     * @param newAnimal
     */
    public synchronized void addOrUpdateAnimal(Animal newAnimal)
    {
        if(this.playerAnimals.containsKey(newAnimal.getName()))
        {
            // Updates current animal
            this.playerAnimals.replace(newAnimal.getName(), newAnimal);
        }
        else
        {
            this.playerAnimals.put(newAnimal.getName(), newAnimal);
        }

        for (String babyName : newAnimal.getBabies().keySet()){
            addOrUpdateAnimal(newAnimal.getBabies().get(babyName));
        }
    }

    /***
     * Removes every animal owned by a player from the list of animals
     * @param playerName
     */
    public synchronized void removeAnimalsOfAPlayer(String playerName){

        for (String animalName: this.playerAnimals.keySet()) {
            if (this.playerAnimals.get(animalName).getOwner().equals(playerName)) {
                this.playerAnimals.remove(animalName);
            }
        }

    }

    /***
     * Removes an animal owned by a player from the list of animals
     * @param animalName
     */
    public synchronized void removeAnimal(String animalName){
        if(this.playerAnimals.containsKey(animalName))
        {
            this.playerAnimals.remove(animalName);
        }
    }

    /***
     * Refreshes position of every animal in the zoo
     * And checks for mate to have coitus with
     */
    public synchronized void moveAnimals(List<Obstacle> obstaclesInZoo)
    {
        for (String animalName : this.getPlayerAnimals().keySet())
        {
            Animal animal = this.getPlayerAnimals().get(animalName);

            // Movement part
            if(!animal.isArrived())
            {
                animal.move(0.5);

                int i = 0;
                Boolean collision = false;

                while(i < obstaclesInZoo.size() && !collision)
                {
                    if(animal.hitObstacle(obstaclesInZoo.get(i)))
                    {
                        System.out.println("BOUM");
                        collision = true;
                    }
                    else
                    {
                        i++;
                    }
                }
                if(collision)
                {
                    animal.setTarget();
                }

                // Mating part
                Boolean hadBabies = false;

                Iterator<Map.Entry<String, Animal>> iterator = this.getPlayerAnimals().entrySet().iterator();
                while(iterator.hasNext() && !hadBabies)
                {
                    Map.Entry<String, Animal> entry = iterator.next();
                    Animal otherAnimal = this.getPlayerAnimals().get(entry.getKey());
                    if(otherAnimal != animal && animal.isAnAdult() && animal.getCanHaveBabies()) {
                        if (animal.intersects(otherAnimal.getPosition())) {
                            if (otherAnimal.getClass().getName().equals(animal.getClass().getName()) && !animal.getSex().equals(otherAnimal.getSex())) {
                                animal.haveBabiesWith(otherAnimal);
                                hadBabies = true;
                            }
                        }
                    }
                }
            }
            else
            {
                animal.setTarget();
            }
        }

    }
}

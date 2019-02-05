package Model.Class.Zoo;

import Model.Class.Network.TempReproduction;
import Model.Class.Player;
import Model.Class.Zoo.Animals.*;
import Model.Class.Client;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.*;

/***
 * Zoo class : contains all information of a zoo
 */
public class Zoo implements Serializable
{
    private HashMap<String, Animal> animalsInZoo = new HashMap<>();
    private List<Obstacle> obstaclesInZoo = new ArrayList<Obstacle>();
    private HashMap<String, Player> playersInZoo = new HashMap<>();

    /***
     * Common constructor implementing obstacles that are used as maps borders.
     * Used for the client
     */
    public Zoo() {
        Obstacle topBorder = new Obstacle(0, -1, 501, 1);
        Obstacle rightBorder = new Obstacle(501, 0, 1, 501);
        Obstacle bottomBorder = new Obstacle(-1, 501, 501, 1);
        Obstacle leftBorder = new Obstacle(-1, -1, 1, 501);

        obstaclesInZoo.add(topBorder);
        obstaclesInZoo.add(rightBorder);
        obstaclesInZoo.add(bottomBorder);
        obstaclesInZoo.add(leftBorder);
    }

    /***
     * Empty constructor for the server (no obstacles needed)
     * @param useless
     */
    public Zoo(String useless){

    }

    //-- Getter and Setter --//

    public HashMap<String, Animal> getAnimalsInZoo() {
        return animalsInZoo;
    }

    public synchronized void setAnimalsInZoo(HashMap<String, Animal> animalsInZoo) {
        this.animalsInZoo = animalsInZoo;
    }

    public List<Obstacle> getObstaclesInZoo() {
        return obstaclesInZoo;
    }

    public synchronized void setObstaclesInZoo(List<Obstacle> obstaclesInZoo) {
        this.obstaclesInZoo = obstaclesInZoo;
    }

    public HashMap<String, Player> getPlayersInZoo() {
        return playersInZoo;
    }

    public synchronized void setPlayersInZoo(HashMap<String, Player> playersInZoo) {
        this.playersInZoo = playersInZoo;
    }

    //-- Methods --//

    /***
     * Adds a specific animal to the Zoo or overrides it if it already exists
     * Also add babies of said animals to the list of the zoo
     * @param newAnimal
     */
    public synchronized void addAnimal(Animal newAnimal){

        if(this.animalsInZoo.containsKey(newAnimal.getName())){
            //overrides current animal
            this.animalsInZoo.replace(newAnimal.getName(), newAnimal);
        }
        else
        {
            this.animalsInZoo.put(newAnimal.getName(), newAnimal);
        }

        for (String babyName : newAnimal.getBabies().keySet()){
            addAnimal(newAnimal.getBabies().get(babyName));
        }

    }

    /***
     * Synchronized two list of animals from two different zoo into one
     * @param otherZoo
     */
    public synchronized void syncAnimals(Zoo otherZoo)
    {
        for(String animalStr : otherZoo.getAnimalsInZoo().keySet())
        {
            //System.out.println("Synchronizing animals...");
            this.addAnimal(otherZoo.getAnimalsInZoo().get(animalStr));
        }
    }

    /***
     * Refreshes position of every animal in the zoo
     * And checks for mate to have coitus with
     */
    public synchronized void moveAnimals()
    {
        for (String animalName : this.getAnimalsInZoo().keySet())
        {
            Animal animal = this.getAnimalsInZoo().get(animalName);

            // Movement part
            if(!animal.isArrived())
            {
                animal.move(0.5);

                int i = 0;
                Boolean isObstacle = false;

                while(i < this.getObstaclesInZoo().size() && !isObstacle)
                {
                    if(animal.intersects(this.getObstaclesInZoo().get(i).getPosition()))
                    {
                        isObstacle = true;
                    }
                    else
                    {
                        i++;
                    }
                }
                if(isObstacle)
                {
                    animal.setTarget();
                }

                // Mating part
                Boolean hadBabies = false;

                Iterator<Map.Entry<String, Animal>> iterator = this.getAnimalsInZoo().entrySet().iterator();
                while(iterator.hasNext() && !hadBabies)
                {
                    Map.Entry<String, Animal> entry = iterator.next();
                    Animal otherAnimal = this.getAnimalsInZoo().get(entry.getKey());
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

    /***
     * Adds a specific player to the Zoo if its not already in
     * Otherwise overrides the player
     * @param newPlayer
     */
    public synchronized void addPlayer (Player newPlayer)
    {
        if(this.playersInZoo.containsKey(newPlayer.getName())){
            //overrides current animal
            this.playersInZoo.replace(newPlayer.getName(), newPlayer);
        }
        else
        {
            this.playersInZoo.put(newPlayer.getName(), newPlayer);
        }
    }

    /***
     * Synchronizes players from different zoo
     * @param otherZoo
     */
    public synchronized void syncPlayer(Zoo otherZoo)
    {
        for(String playerName : otherZoo.getPlayersInZoo().keySet())
        {
            //System.out.println("Synchronizing players...");
            this.addPlayer(otherZoo.getPlayersInZoo().get(playerName));
        }
    }

    /***
     * Adds a specific obstacle to the Zoo
     * @param newObstacle
     */
    public synchronized void addObstacle (Obstacle newObstacle)
    {
        this.obstaclesInZoo.add(newObstacle);
    }
}

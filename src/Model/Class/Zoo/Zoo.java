package Model.Class.Zoo;

import Model.Class.Player;
import Model.Class.Zoo.Animals.*;

import java.io.Serializable;
import java.util.*;

/***
 * Zoo class : contains all information of a zoo
 */
public class Zoo implements Serializable
{
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

    //-- Getter and Setter --//

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
     * Adds a specific player to the Zoo if its not already in
     * Otherwise overrides the player
     * @param newPlayer
     */
    public synchronized void addPlayer (Player newPlayer)
    {
        System.out.println("Adding player...");
        this.playersInZoo.put(newPlayer.getName(), newPlayer);
    }

    /***
     * Update a specific player
     * @param newPlayer
     */
    public synchronized void updatePlayer (Player newPlayer)
    {
        System.out.println("Updating player...");

        if(this.playersInZoo.containsKey(newPlayer.getName()))
        {
            //overrides current animal
            this.playersInZoo.replace(newPlayer.getName(), newPlayer);
        }
    }

    /***
     * Removes a player and its animals from the zoo using its name
     * @param aPlayer
     */
    public synchronized void removePlayer (Player aPlayer)
    {
        System.out.println("Removing player...");

        if(this.playersInZoo.containsKey(aPlayer.getName())){
            //overrides current animal
            this.playersInZoo.remove(aPlayer.getName());
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

    public boolean playerIsOnline(String playerName){
        return this.playersInZoo.containsKey(playerName);
    }
    /***
     * Adds a specific obstacle to the Zoo
     * @param newObstacle
     */
    public synchronized void addObstacle (Obstacle newObstacle)
    {
        this.obstaclesInZoo.add(newObstacle);
    }

    /***
     * Synchronises two list of animals from two different zoo into one
     * @param otherZoo
     */
    public synchronized void syncAnimals(Zoo otherZoo)
    {
        /*for(String animalStr : otherZoo.getAnimalsInZoo().keySet())
        {
            //System.out.println("Synchronizing animals...");
            this.addOrUpdateAnimal(otherZoo.getAnimalsInZoo().get(animalStr));
        }*/
    }
}

package Model.Class.Zoo;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Entity.Obstacle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * Zoo class : contains all information of a zoo
 */
public class Zoo implements Serializable {
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

        Obstacle bottomLeftTree = new Obstacle(133, 332, 20, 80);
        Obstacle topRightTree = new Obstacle(361, 70, 20, 80);

        Obstacle middleRightRocks = new Obstacle(405, 283, 80, 50);

        obstaclesInZoo.add(topBorder);
        obstaclesInZoo.add(rightBorder);
        obstaclesInZoo.add(bottomBorder);
        obstaclesInZoo.add(leftBorder);

        obstaclesInZoo.add(bottomLeftTree);
        obstaclesInZoo.add(topRightTree);

        obstaclesInZoo.add(middleRightRocks);
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
    public synchronized void addPlayer(Player newPlayer) {
        System.out.println("Adding player...");
        this.playersInZoo.put(newPlayer.getName(), newPlayer);
    }

    /***
     * Update a specific player
     * @param newPlayer
     */
    public synchronized void updatePlayer(Player newPlayer) {
        //System.out.println("Updating player...");

        if (this.playersInZoo.containsKey(newPlayer.getName())) {
            //overrides current animals
            this.playersInZoo.replace(newPlayer.getName(), newPlayer);
        }
    }

    /***
     * Removes a player and its animals from the zoo using its name
     * @param aPlayer
     */
    public synchronized void removePlayer(Player aPlayer) {
        System.out.println("Removing player...");

        if (this.playersInZoo.containsKey(aPlayer.getName())) {
            //overrides current animal
            this.playersInZoo.remove(aPlayer.getName());
        }
    }

    /***
     * Returns a list containing all animals in the zoo
     */
    public HashMap<String, Animal> getAnimalsInZoo() {
        HashMap<String, Animal> allAnimals = new HashMap<>();

        for (String playerName : this.getPlayersInZoo().keySet()) {
            for (String animalName : this.getPlayersInZoo().get(playerName).getPlayerAnimals().keySet()) {
                allAnimals.put(animalName, this.getPlayersInZoo().get(playerName).getPlayerAnimals().get(animalName));
            }
        }

        return allAnimals;
    }

}

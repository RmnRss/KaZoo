package Model.Class.Zoo;

import Model.Class.Zoo.Animals.*;
import Model.Class.Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * Zoo class : contains all information of a zoo
 */
public class Zoo implements Serializable
{
    private HashMap<String, Animal> animalsInZoo = new HashMap<>();
    private List<Obstacle> obstaclesInZoo = new ArrayList<Obstacle>();
    private HashMap<String, Client> clientsInZoo = new HashMap<>();

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

    public void setAnimalsInZoo(HashMap<String, Animal> animalsInZoo) {
        this.animalsInZoo = animalsInZoo;
    }

    public List<Obstacle> getObstaclesInZoo() {
        return obstaclesInZoo;
    }

    public void setObstaclesInZoo(List<Obstacle> obstaclesInZoo) {
        this.obstaclesInZoo = obstaclesInZoo;
    }

    public HashMap<String, Client> getClientsInZoo() {
        return clientsInZoo;
    }

    public void setClientsInZoo(HashMap<String, Client> clientsInZoo) {
        this.clientsInZoo = clientsInZoo;
    }

    //-- Methods --//

    /***
     * Adds a specific animal to the Zoo
     * @param newAnimal
     */
    public void addAnimal(Animal newAnimal){
        if(this.animalsInZoo.containsKey(newAnimal.getName())){
            //overrides current animal
            this.animalsInZoo.replace(newAnimal.getName(), newAnimal);
        }
        else
        {
            this.animalsInZoo.put(newAnimal.getName(), newAnimal);
        }

    }

    /***
     * Adds a specific client to the Zoo
     * @param newClient
     */
    public void addClient (Client newClient)
    {
        this.clientsInZoo.put(newClient.getName(), newClient);
    }

    /***
     * Adds a specific obstacle to the Zoo
     * @param newObstacle
     */
    public void addObstacle (Obstacle newObstacle)
    {
        this.obstaclesInZoo.add(newObstacle);
    }
}

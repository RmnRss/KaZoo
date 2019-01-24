package Model.Class.Zoo;

import Model.Class.Zoo.Animals.*;
import Model.Class.Client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/***
 * Zoo class : contains all information of a zoo
 */
public class Zoo implements Serializable
{
    private List<Animal> animalsInZoo = new ArrayList<Animal>();
    private List<Obstacle> obstaclesInZoo = new ArrayList<Obstacle>();
    private List<Client> clientsInZoo = new ArrayList<Client>();

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

    public List<Animal> getAnimalsInZoo() {
        return animalsInZoo;
    }

    public void setAnimalsInZoo(List<Animal> animalsInZoo) {
        this.animalsInZoo = animalsInZoo;
    }

    public List<Obstacle> getObstaclesInZoo() {
        return obstaclesInZoo;
    }

    public void setObstaclesInZoo(List<Obstacle> obstaclesInZoo) {
        this.obstaclesInZoo = obstaclesInZoo;
    }

    public List<Client> getClientsInZoo() {
        return clientsInZoo;
    }

    public void setClientsInZoo(List<Client> clientsInZoo) {
        this.clientsInZoo = clientsInZoo;
    }

    //-- Methods --//

    /***
     * Adds a specific animal to the Zoo
     * @param newAnimal
     */
    public void addAnimal(Animal newAnimal){
        this.animalsInZoo.add(newAnimal);
    }

    /***
     * Adds a specific client to the Zoo
     * @param newClient
     */
    public void addClient (Client newClient)
    {
        this.clientsInZoo.add(newClient);
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

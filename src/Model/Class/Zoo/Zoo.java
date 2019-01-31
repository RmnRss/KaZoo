package Model.Class.Zoo;

import Model.Class.Zoo.Animals.*;
import Model.Class.Client;
import javafx.scene.canvas.GraphicsContext;

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

    public synchronized void setAnimalsInZoo(HashMap<String, Animal> animalsInZoo) {
        this.animalsInZoo = animalsInZoo;
    }

    public List<Obstacle> getObstaclesInZoo() {
        return obstaclesInZoo;
    }

    public synchronized void setObstaclesInZoo(List<Obstacle> obstaclesInZoo) {
        this.obstaclesInZoo = obstaclesInZoo;
    }

    public HashMap<String, Client> getClientsInZoo() {
        return clientsInZoo;
    }

    public synchronized void setClientsInZoo(HashMap<String, Client> clientsInZoo) {
        this.clientsInZoo = clientsInZoo;
    }

    //-- Methods --//

    /***
     * Adds a specific animal to the Zoo or overides it, if it already exists
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

    }

    public synchronized void syncAnimals(Zoo otherZoo)
    {
        for(String animalStr : otherZoo.getAnimalsInZoo().keySet())
        {

            System.out.println("Syncing...");

            if(animalsInZoo.containsKey(animalStr))
            {
                animalsInZoo.replace(animalStr, otherZoo.getAnimalsInZoo().get(animalStr));

                System.out.println(">> 1 " + getAnimalsInZoo().get(getAnimalsInZoo().keySet().iterator().next()).getName() + " " + getAnimalsInZoo().get(getAnimalsInZoo().keySet().iterator().next()).getPosition().getX()+ " " + getAnimalsInZoo().get(getAnimalsInZoo().keySet().iterator().next()).getPosition().getY());

                System.out.println(">> 2 " + otherZoo.getAnimalsInZoo().get(otherZoo.getAnimalsInZoo().keySet().iterator().next()).getName() + " " + otherZoo.getAnimalsInZoo().get(otherZoo.getAnimalsInZoo().keySet().iterator().next()).getPosition().getX()+ " " + otherZoo.getAnimalsInZoo().get(otherZoo.getAnimalsInZoo().keySet().iterator().next()).getPosition().getY());

            }
            else
            {
                animalsInZoo.put(animalStr, otherZoo.getAnimalsInZoo().get(animalStr));
            }
        }
    }

    /***
     * Refreshes position
     */
    public synchronized void moveAnimals()
    {
        for (String animalName : this.getAnimalsInZoo().keySet()) {
            Animal animal = this.getAnimalsInZoo().get(animalName);
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
            }
            else
            {
                animal.setTarget();
            }
        }
    }

    /***
     * Adds a specific client to the Zoo
     * @param newClient
     */
    public synchronized void addClient (Client newClient)
    {
        this.clientsInZoo.put(newClient.getName(), newClient);
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

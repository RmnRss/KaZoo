package Model.Class.Zoo;

import Model.Class.Animals.*;
import Model.Class.Client;

import java.util.ArrayList;
import java.util.List;

public class Zoo
{
    private List<Animal> animalsInZoo = new ArrayList<Animal>();
    private List<Obstacle> obstaclesInZoo = new ArrayList<Obstacle>();
    private List<Client> clientsInZoo = new ArrayList<Client>();

    public Zoo() {
    }

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
}

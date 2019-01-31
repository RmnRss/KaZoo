package Model.Class.Network;

import Model.Class.Zoo.Animals.Bear;
import Model.Class.Zoo.Zoo;

import java.net.*;
import java.io.*;

/***
 * Not fonctionnal yet
 */
class Service implements Runnable
{
    private Socket clientSocket;
    private ClientCounter counter;
    private String msg = "null";
    Zoo zoo;

    /***
     * Constructor
     * @param clientSocket
     * @param pCount
     */

    public Service(Socket clientSocket, ClientCounter pCount, Zoo zooLol)
    {
        this.clientSocket = clientSocket;
        this.counter = pCount;
        this.zoo = zooLol;

        System.out.println("Constructor : " + zooLol.hashCode());
    }

    /***
     * Contains the task the Service should handle
     */
    public void run()
    {
        boolean isStopped = false;

        try
        {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            //BufferedReader bf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while (!isStopped)
            {
                //Reading client object
                Zoo clientZoo = (Zoo)in.readObject();

                /*for (String animalName : clientZoo.getAnimalsInZoo().keySet()) {
                    zoo.addAnimal(clientZoo.getAnimalsInZoo().get(animalName));
                }*/

                zoo.syncAnimals(clientZoo);

                System.out.println(">> before " + clientZoo.getAnimalsInZoo().get(clientZoo.getAnimalsInZoo().keySet().iterator().next()).getName() + " " + clientZoo.getAnimalsInZoo().get(clientZoo.getAnimalsInZoo().keySet().iterator().next()).getPosition().getX()+ " " + clientZoo.getAnimalsInZoo().get(clientZoo.getAnimalsInZoo().keySet().iterator().next()).getPosition().getY());

                zoo.moveAnimals();

                System.out.println(">> after " + zoo.getAnimalsInZoo().get(zoo.getAnimalsInZoo().keySet().iterator().next()).getName() + " " + zoo.getAnimalsInZoo().get(zoo.getAnimalsInZoo().keySet().iterator().next()).getPosition().getX()+ " " + zoo.getAnimalsInZoo().get(zoo.getAnimalsInZoo().keySet().iterator().next()).getPosition().getY());

                //Sending to client
                out.reset();
                out.writeObject(zoo);

                out.flush();

                //String message = bf.readLine();
            }

            clientSocket.close();
            System.out.println("Closed");

        } catch (Exception e)
        {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
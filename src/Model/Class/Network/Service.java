package Model.Class.Network;

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
    Zoo serviceZoo;

    /***
     * Service constructor
     * @param clientSocket
     * @param clientCounter
     * @param serverZoo
     */
    public Service(Socket clientSocket, ClientCounter clientCounter, Zoo serverZoo)
    {
        this.clientSocket = clientSocket;
        this.counter = clientCounter;
        this.serviceZoo = serverZoo;
    }

    /***
     * Contains the task the Service should handle
     */
    public void run()
    {
        boolean isStopped = false;
        String message;
        int clientNumber;
        counter.incCount();
        clientNumber = counter.getCount();

        try
        {
            // Initialize the streams for every client
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            while (!isStopped)
            {
                System.out.println("Client n°" + clientNumber);
                // Reading client object
                Zoo clientZoo = (Zoo)in.readObject();

                // Merging animals of the general serviceZoo with the ones from the client
                serviceZoo.syncAnimals(clientZoo);

                // Moving animals in the general serviceZoo
                serviceZoo.moveAnimals();

                // Sending modified Zoo to the client
                out.reset();
                out.writeObject(serviceZoo);
                out.flush();
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
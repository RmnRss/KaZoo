package Model.Class.Network;

import Model.Class.Zoo.Player;
import Model.Class.Zoo.Zoo;

import java.net.*;
import java.io.*;

/***
 * Tasks to realise by the server for every client
 */
class Service implements Runnable
{
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private ClientCounter counter;

    private Player playerInService;
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
        int clientNumber;

        //Counting the number of client
        counter.incCount();
        clientNumber = counter.getCount();

        try
        {
            // Initializing the streams for every client
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            // Reading the player object from the client
            playerInService = (Player)in.readObject();
            playerInService.setColor(clientNumber-1);

            // Updating the player list with the new player
            serviceZoo.addPlayer(playerInService);

            System.out.println(playerInService.getName() + " is now online");

            // While the client is online
            while (!isStopped)
            {
                // Sending modified Zoo to the client
                out.reset();
                out.writeObject(serviceZoo);
                out.flush();

                // Reading client zoo
                playerInService = (Player) in.readObject();

                // Merging animals of the general serviceZoo with the ones from the client
                serviceZoo.updatePlayer(playerInService);
            }

        } catch (Exception e)
        {
            System.err.println("Erreur : " + e);
            e.printStackTrace();

            // Removes a player from the zoo and the count
            counter.decCount();
            closeService(in, out, clientSocket);
        }
    }

    /***
     * Closes the different streams and socket of the service
     * Called when an excpetion is caught by the service
     * @param in
     * @param out
     * @param cSocket
     */
    void closeService(ObjectInputStream in, ObjectOutputStream out, Socket cSocket)
    {
        System.out.println(playerInService.getName() + " disconnecting");

        serviceZoo.removePlayer(playerInService);

        try {
            out.close();
        } catch (IOException e) {
            System.out.println("Problem closing the output stream");
        }

        try {
            in.close();
        } catch (IOException e) {
            System.out.println("Problem closing the input stream");
        }

        try {
            cSocket.close();
        } catch (IOException e) {
            System.out.println("Problem closing the socket");
        }

    }
}
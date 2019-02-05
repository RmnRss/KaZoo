package Model.Class.Network;

import Model.Class.Player;
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

            // Reading the zoo from the client
            playerInService = (Player)in.readObject();

            // Updating the player list with the new player
            serviceZoo.addPlayer(playerInService);
            playerInService.setColor(clientNumber-1);

            // While the client is online
            while (!isStopped)
            {
                // Sending modified Zoo to the client
                out.reset();
                out.writeObject(serviceZoo);
                out.flush();

                // System.out.println("Client n°" + clientNumber);
                // Reading client zoo
                playerInService = (Player) in.readObject();

                // Merging animals of the general serviceZoo with the ones from the client
                serviceZoo.updatePlayer(playerInService);
            }

            clientSocket.close();
            System.out.println("Closed");

        } catch (Exception e)
        {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
            counter.decCount();
            closeService(in, out, clientSocket);
        }
    }

    /***
     * Closes the different streams and socket of the service
     * @param in
     * @param out
     * @param cSocket
     */
    void closeService(ObjectInputStream in, ObjectOutputStream out, Socket cSocket){

        // TODO: Remove animals of the disconnecting player
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
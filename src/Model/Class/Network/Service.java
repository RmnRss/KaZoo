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
            Zoo clientZoo = (Zoo)in.readObject();

            // Updating the player list with the new player
            serviceZoo.syncPlayer(clientZoo);
            System.out.println("Il y a " + serviceZoo.getPlayersInZoo().size() + " players dans le zoo");
            Player newPlayer = serviceZoo.getPlayersInZoo().get(clientZoo.getPlayersInZoo().keySet().iterator().next());
            newPlayer.setColor(clientNumber-1);
            System.out.println("Couleur du client n° " + clientNumber + ": " + newPlayer.getColor());

            // While the client is online
            while (!isStopped)
            {
                // System.out.println("Client n°" + clientNumber);
                // Reading client zoo
                clientZoo = (Zoo)in.readObject();

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
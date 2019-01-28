package Model.Class.Network;

import Model.Class.Zoo.Animals.Animal;
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
    private Zoo serverZoo;

    /***
     * Constructor
     * @param clientSocket
     * @param pCount
     */

    public Service(Socket clientSocket, ClientCounter pCount, Zoo serverZoo)
    {
        this.clientSocket = clientSocket;
        this.counter = pCount;
        this.serverZoo = serverZoo;

        System.out.println("Constructor : " + serverZoo.hashCode());
    }

    /***
     * Contains the task the Service should handle
     */
    public void run()
    {

        //Counting clients
        counter.incCount();
        System.out.println(counter.getCount() + " Client(s)");

        int clientNumber = counter.getCount();

        try {

            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            while(true) {

                System.out.println("Client number : " + clientNumber);
                //System.out.println("Server Hash in service : " + serverZoo.hashCode());

                System.out.println("Sending...");
                outputStream.writeObject(serverZoo);
                outputStream.flush();
                System.out.println("Send ServerZoo of size : " + serverZoo.getAnimalsInZoo().size());

                System.out.println("Receiving...");
                Zoo tempZoo = (Zoo) inputStream.readObject();

                System.out.println("Received : " + tempZoo.getAnimalsInZoo().size());

                for (String animalName : tempZoo.getAnimalsInZoo().keySet()) {
                    serverZoo.addAnimal(tempZoo.getAnimalsInZoo().get(animalName));
                }

                if (clientSocket.isClosed())
                {
                    //Affichage de la déconnexion d'un client
                    counter.decCount();
                    System.out.println("Déconnexion d'un client");
                    System.out.println(counter.getCount() + " Clients");
                }
            }


            //clientSocket.close();

        } catch (Exception e) {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
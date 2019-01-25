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
    private Zoo serverZoo = new Zoo("");

    /***
     * Constructor
     * @param clientSocket
     * @param pCount
     */

    public Service(Socket clientSocket, ClientCounter pCount)
    {
        this.clientSocket = clientSocket;
        this.counter = pCount;
    }

    /***
     * Contains the task the Service should handle
     */
    public void run()
    {

        //Counting clients
        counter.incCount();
        System.out.println(counter.getCount() + " Client(s)");

        try {

            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());


            while(true) {
                Zoo tempZoo = (Zoo) in.readObject();

                for (Animal animal : tempZoo.getAnimalsInZoo()) {
                    serverZoo.addAnimal(animal);
                }

                System.out.println(serverZoo.getAnimalsInZoo().size());

                out.writeObject(serverZoo);
                out.flush();

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
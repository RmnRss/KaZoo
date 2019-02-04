package Model.Class;

import Model.Class.Zoo.Animals.Bear;
import Model.Class.Zoo.Zoo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * Single thread server
 * Received all information about all zoo and merged them into one
 * Then sends those information to all clients so they can all display it
 */
public class Server {
    public static final int PORT = 6789;


    public static void main(String args[]) throws Exception
    {
        ServerSocket serSocket = new ServerSocket(PORT);
        System.out.println("Attente de connexions, serveur prêt");
        boolean isStopped = false;

        try
        {
            Zoo KaZoo = new Zoo("");

            Socket cSocket = serSocket.accept();
            System.out.println("Connexion client");

            ObjectOutputStream out = new ObjectOutputStream(cSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());

            BufferedReader bf = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));

            while (!isStopped)
            {
                //Reading client object
                Zoo clientZoo = (Zoo)in.readObject();

                for (String animalName : clientZoo.getAnimalsInZoo().keySet()) {
                    KaZoo.addAnimal(clientZoo.getAnimalsInZoo().get(animalName));
                }

                //KaZoo.addAnimal(new Bear("Boobs", "Fragile", "Didier"));

                //Sending to client
                out.writeObject(KaZoo);
                out.flush();

                //String message = bf.readLine();
            }

                serSocket.close();
                System.out.println("Closed");


        } catch (Exception e)
        {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}

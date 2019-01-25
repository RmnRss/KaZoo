package Model.Class;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Zoo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static javafx.application.Application.launch;

/***
 * Received all information about all zoo and merged them into one
 * Then sends those information to all clients so they can all display it
 */
public class Server {
    public static final int PORT = 6789;


    public static void main(String args[]) throws Exception {
        ServerSocket serSocket = new ServerSocket(PORT);
        System.out.println("Attente de connexions, serveur prêt");
        try
        {
            Zoo KaZoo = new Zoo("");

            Socket cSocket = serSocket.accept();
            System.out.println("Connexion client");

            ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(cSocket.getOutputStream());
            BufferedReader bf = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
            Zoo tempZoo = (Zoo)in.readObject();

            for (Animal animal : tempZoo.getAnimalsInZoo()) {
                KaZoo.addAnimal(animal);
            }

            out.writeObject(KaZoo);
            out.flush();

            String message = bf.readLine();
            if(message.equals("Exit")){
                cSocket.close();
                System.out.println("Closed");
            }


        } catch (Exception e)
        {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}

package Model.Class;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Zoo;

import java.io.ObjectInputStream;
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

        Zoo KaZoo = new Zoo("");

        Socket cSocket = serSocket.accept();
        System.out.println("Connexion client");

        while (cSocket.isConnected()){
            ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());
            Zoo tempZoo = (Zoo)in.readObject();

            for (Animal animal : tempZoo.getAnimalsInZoo()) {
                KaZoo.addAnimal(animal);
            }
        }
    }
}

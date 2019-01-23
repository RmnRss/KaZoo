package Model.Class;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Zoo;

import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static javafx.application.Application.launch;

public class Server {
    public static final int PORT = 6789;


    public static void main(String args[]) throws Exception {
        ServerSocket serSocket = new ServerSocket(PORT);
        System.out.println("Attente de connexions, serveur prêt");

        Zoo KaZoo = new Zoo("");

        //while (true) {
        Socket cSocket = serSocket.accept();
        ObjectInputStream in = new ObjectInputStream(cSocket.getInputStream());
        Zoo tempZoo = (Zoo)in.readObject();

        for (Animal animal : tempZoo.getAnimalsInZoo()) {
            KaZoo.addAnimal(animal);
        }
        //new Thread(new Zoo(cSocket)).start();
        //}



    }
}

package Model.Class.Network;

import Model.Class.Zoo.Zoo;

import java.net.*;
import java.io.*;

public class MultiClientServer
{
    public static final int PORT = 6789;

    public static void main(String[] arguments)
    {
        ClientCounter count = new ClientCounter();
        Zoo serverZoo = new Zoo("");

        try
        {
            // Sockets
            ServerSocket socketAttente;
            socketAttente = new ServerSocket(PORT);

            System.out.println("Attente d'une connexion...");

            do
            {
                // Attente bloquante
                Socket s = socketAttente.accept();
                System.out.println("Connexion en cours...");

                // Creation du service dans un nouveau processus
                Thread t = new Thread(new Service(s, count, serverZoo));

                // Lance le thread
                t.start();

            } while (true);

            // socketAttente.close();
        } catch (Exception e)
        {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
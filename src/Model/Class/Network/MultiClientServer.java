package Model.Class.Network;

import Model.Class.Zoo.Zoo;
import java.net.*;

/***
 * Multi thread server
 * Launches a thread for every client
 */
public class MultiClientServer
{
    public static final int PORT = 6789;

    public static void main(String[] arguments)
    {
        ClientCounter count = new ClientCounter();
        Zoo serverZoo = new Zoo("");

        boolean isStopped = false;

        try
        {
            // Sockets
            ServerSocket serverSocket;
            serverSocket = new ServerSocket(PORT);

            System.out.println("Attente d'une connexion...");

            // While server is online
            // For every client creates a new sockets
            // Launches a new thread running a Service
            do
            {
                // Waits for a connexion to the server socket
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion en cours...");

                // Creates a new threads running another service
                Service newService = new Service(clientSocket, count, serverZoo);
                Thread t = new Thread(newService);

                // Launches the thread
                t.start();

            } while (!isStopped);

            serverSocket.close();
        } catch (Exception e)
        {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
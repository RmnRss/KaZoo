package Model.Class.Network;

import java.net.*;
import java.io.*;
import java.util.Arrays;

/***
 * Not fonctionnal yet
 */
class Service implements Runnable
{
    private Socket maSocket;
    private ClientCounter count;
    private String msg = "null";

    public Service(Socket clientSocket, ClientCounter pCount)
    {
        this.maSocket = clientSocket;
        this.count = pCount;
    }

    public void run()
    {

        //Count client
        count.incCompteur();
        System.out.println(count.getCompteurClient() + " Connexion(s)");

        try {

            BufferedReader bfFromClient = new BufferedReader(new InputStreamReader(maSocket.getInputStream()));
            PrintWriter pwToClient = new PrintWriter(new OutputStreamWriter(maSocket.getOutputStream()));

            while (!msg.equals("stop"))
            {
                //Reception du message
                msg = bfFromClient.readLine();
                System.out.println( "Message client : " + msg );
                Thread.sleep(500);

                //Parsing du message
                String[] msgPart = msg.split(" ");
                int a = Integer.parseInt(msgPart[1]);
                int b = Integer.parseInt(msgPart[2]);

                //Choix de l'operation
                //Et calcul
                switch (msgPart[0])
                {
                    case "ADD":
                        msg = Integer.toString(a+b);
                        break;

                    case "SOUS" :
                        msg = Integer.toString(a-b);
                        break;

                    case "MUL":
                        msg = Integer.toString(a*b);
                        break;

                    case "DIV":
                        msg = Integer.toString(a/b);
                        break;

                    default:
                        msg = "Operation invalide";
                        break;
                }

                //Envoie du resutlat
                pwToClient = new PrintWriter(new OutputStreamWriter(maSocket.getOutputStream()));
                pwToClient.println("Le resultat est " + msg);
                pwToClient.flush();
            }

            //Affichage de la déconnexion d'un client
            count.decCompteur();
            System.out.println(count.getCompteurClient() + " Connexions");

            pwToClient.close();
            bfFromClient.close();
            maSocket.close();

        } catch (Exception e) {
            System.err.println("Erreur : " + e);
            e.printStackTrace();
            System.exit(1);
        }
    }
}
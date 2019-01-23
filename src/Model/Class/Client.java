package Model.Class;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final int PORT = 6789;

    public static void main(String args[]) throws Exception {
        String str;
        String echo;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", PORT);
        PrintWriter outToServer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Entrez le nom du client:");
        str = inFromUser.readLine();
        outToServer.println(str);
        outToServer.flush();
        System.out.println("Votre service ECHO est pret :");

        inFromServer.close();
        outToServer.close();
        clientSocket.close();
    }
}

package Model.Class;

import Model.Class.Zoo.Zoo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 6789;

    public static void main(String args[]) throws Exception {
        ServerSocket serSocket = new ServerSocket(PORT);
        System.out.println("Attente de connexions, serveur prêt");

        //while (true) {
            Socket cSocket = serSocket.accept();
            BufferedReader bf = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));

            String msg = bf.readLine();
            System.out.println(msg);
            bf.close();
            //new Thread(new Zoo(cSocket)).start();
        //}

    }
}

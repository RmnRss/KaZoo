package Model.Class.Network;

public class ClientCounter {

    private int compteurClient;

    public ClientCounter() {
        this.compteurClient=0;
    }

    public synchronized void incCompteur(){
        compteurClient++;
    }

    public synchronized void decCompteur(){
        compteurClient--;
    }

    public int getCompteurClient() {
        return compteurClient;
    }

}

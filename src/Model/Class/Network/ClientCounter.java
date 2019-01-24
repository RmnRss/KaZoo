package Model.Class.Network;

/***
 * Not used yet
 */
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

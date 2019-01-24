package Model.Class.Network;

/***
 * Counts the number of clients connected to the server
 */
public class ClientCounter
{
    private int count;

    /***
     * Constructor intitialize the
     */
    public ClientCounter() {
        this.count =0;
    }

    /***
     * Adds one to the counter
     * Adds a client
     */
    public synchronized void incCount()
    {
        count++;
    }

    /**
     * Decrease the counter from one
     * Removes one Client
     */
    public synchronized void decCount()
    {
        count--;
    }

    /***
     * Returns the number of clients connected to the server
     * @return
     */
    public int getCount()
    {
        return count;
    }

}

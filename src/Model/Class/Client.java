package Model.Class;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Animals.Bear;
import Model.Class.Zoo.Animals.Penguin;
import Model.Class.Zoo.Animals.Turtle;
import Model.Class.Zoo.Zoo;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

import static java.lang.Thread.sleep;

/***
 * Client class Runs the interface and connects to the server
 * Every client as its own zoo
 */
public class Client extends Application
{
    public static final int PORT = 6789;

    private String ipRomain = "192.168.43.92";
    private String ipSandra = "192.168.43.106";

    private Socket clientSocket;

    private Zoo clientKaZoo = new Zoo();
    private String name;

    // Images declaration
    private Image map = new Image("resources/img/map.png");

    private Image imgBear;
    private Image imgPenguin;
    private Image imgTurtle;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    /***
     * Empty Constructor
     * @throws IOException
     */
    public Client() throws IOException {
    }

    public static void main(String args[]) throws Exception {
        launch(args);
    }

    /***
     * Main loop to run the Client
     * @param window
     * @throws Exception
     */
    @Override
    public void start(Stage window) throws Exception
    {
        window.setTitle("clientKaZoo");

        /*
        *       DATA SETS
        */

        Player michel = new Player("Michel");
        Penguin pigloo = new Penguin("Pig","Male", michel.getName());
        Turtle franklin = new Turtle("Fran", "Male", michel.getName());
        Bear winny = new Bear("Win", "Female", michel.getName());
        clientKaZoo.addPlayer(michel);

        clientKaZoo.addAnimal(pigloo);
        clientKaZoo.addAnimal(franklin);
        clientKaZoo.addAnimal(winny);
        michel.setOwnedAnimals(clientKaZoo.getAnimalsInZoo());

        /*Player didier = new Player("Didier");
        Penguin pigloo = new Penguin("Pigloo","Male", didier.getName());
        Turtle franklin = new Turtle("Franklin", "Male", didier.getName());
        Bear winny = new Bear("Winny", "Male", didier.getName());
        clientKaZoo.addPlayer(didier);

        clientKaZoo.addAnimal(pigloo);
        clientKaZoo.addAnimal(franklin);
        clientKaZoo.addAnimal(winny);
        didier.setOwnedAnimals(clientKaZoo.getAnimalsInZoo());*/

        /*name = "Thierry";
        Penguin pigloo = new Penguin("vdvqsdcs","Male", this.name);
        Turtle franklin = new Turtle("fefq", "Male", this.name);
        Bear winny = new Bear("erzgrz", "Female", this.name);*/

        /*
        *       JAVAFX INIT
        */

        // Base group that contains all our nodes (all scene objects)
        Group root = new Group();

        //Setting the main Scene
        Scene zooScene = new Scene(root);
        window.setScene(zooScene);

        //Creating Canvas
        int mapSide = 500;
        Canvas zooCanvas = new Canvas(mapSide, mapSide);

        //Linking canvas to our root
        root.getChildren().add(zooCanvas);

        // Object to draw inputStream the Canvas
        GraphicsContext gc = zooCanvas.getGraphicsContext2D();

        // Other way to draw
        // gc.drawImage(pigloo.getSprite().getImage(), 0,0 , pigloo.getSprite().getWidth(), pigloo.getSprite().getHeight(), pigloo.getSprite().getX(), pigloo.getSprite().getY(), pigloo.getSprite().getWidth(), pigloo.getSprite().getHeight());

        /*
         *          NETWORK INITIALISATION
         */

        clientSocket = new Socket("localhost", PORT);
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());

        sendInfoToServer();

        final long startNanoTime = System.nanoTime();

        // Main loop used to draw on the canvas avery frame
        // Also used to update the local zoo of the client from the server
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // TODO : Make this part dynamic

                double t = (currentNanoTime - startNanoTime) / 10000000000.0;
                gc.drawImage(map , 0,0 );

                System.out.println("Size clientZoo :" + clientKaZoo.getAnimalsInZoo().size());
                try {

                    sendInfoToServer();
                    receiveInfoFromServer();
                    display(clientKaZoo.getAnimalsInZoo(), gc);

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        window.show();
    }

    /***
     * Called every frame sends information about the zoo to the server
     * @throws IOException
     */
    public void sendInfoToServer() throws IOException
    {
        System.out.println("Sending...");
        outputStream.reset();
        outputStream.writeObject(clientKaZoo);
        outputStream.flush();
    }

    /***
     * Called to receive information : Zoo from the server
     * Then syncs the players and the animals
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void receiveInfoFromServer() throws IOException, ClassNotFoundException, InterruptedException
    {
        System.out.println("Receiving...");
        // Receiving Zoos
        Zoo zooFromServer = (Zoo) inputStream.readObject();

        // Merging Zoo
        clientKaZoo.syncPlayer(zooFromServer);
        clientKaZoo.syncAnimals(zooFromServer);
    }

    /***
     * Called when the window closed
     * Closes the streams and the client
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void stop() throws IOException, InterruptedException
    {
        System.out.println("Closing");

        sleep(1000);

        // TODO : close streams and socket
    }

    /***
     * Displays a list of animals of the canvas
     * @param animalsToDisplay
     * @param gc
     */
    public void display(HashMap<String, Animal> animalsToDisplay, GraphicsContext gc){
        for (String animalName : animalsToDisplay.keySet()){
            Animal animal = animalsToDisplay.get(animalName);
            Player player = clientKaZoo.getPlayersInZoo().get(animal.getOwner());

            if (animal instanceof Bear) {
                animal.render(gc, selectImgBear(player.getColor()));
                display(animal.getBabies(), gc);
            } else {
                if (animal instanceof Penguin) {
                    animal.render(gc, selectImgPenguin(player.getColor()));
                    display(animal.getBabies(), gc);
                } else {
                    if (animal instanceof Turtle) {
                        animal.render(gc, selectImgTurtle(player.getColor()));
                        display(animal.getBabies(), gc);
                    }
                }
            }
        }
    }

    /***
     * Allows to select the color of a bear
     * The color parameters allows to select the right image with the corresponding color
     * @param color
     * @return
     */
    public Image selectImgBear(int color){
        return new Image("resources/img/triangle" + color + ".png");
    }

    /***
     * Allows to select the color of a penguin
     * The color parameters allows to select the right image with the corresponding color
     * @param color
     * @return
     */
    public Image selectImgPenguin(int color){
        return new Image("resources/img/rectangle" + color + ".png");
    }

    /***
     * Allows to select the color of a turtle
     * The color parameters allows to select the right image with the corresponding color
     * @param color
     * @return
     */
    public Image selectImgTurtle(int color){
        return new Image("resources/img/circle" + color + ".png");
    }

    //-- Getter and Setter --//

    public String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }
}

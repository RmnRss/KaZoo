package Model.Class.Network;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Animals.Bear;
import Model.Class.Zoo.Animals.Penguin;
import Model.Class.Zoo.Animals.Turtle;
import Model.Class.Zoo.Player;
import Model.Class.Zoo.Zoo;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import static java.lang.Thread.sleep;

/***
 * Client class Runs the interface and connects to the server
 */
public class Client extends Application {
    public static final int PORT = 6789;

    private Socket clientSocket;

    private Zoo clientKaZoo = new Zoo();
    private Player currentPlayer;

    // Images declaration
    private Image map = new Image("resources/img/map.png");

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
    public void start(Stage window) throws Exception {
        window.setTitle("clientKaZoo");

        /*
         *       DATA SETS
         *       Comments and uncomment to add a specific player and its animal to the server
         */

        currentPlayer = new Player("Michel");
        Penguin pigloo = new Penguin("Loulou", "Male", currentPlayer, "adult");
        Turtle franklin = new Turtle("Foufou", "Male", currentPlayer, "adult");
        Bear winny = new Bear("Yvette", "Female", currentPlayer, "adult");

        /*currentPlayer = new Player("Didier");
        Penguin pigloo = new Penguin("Pigloo","Male", currentPlayer, "adult");
        Turtle franklin = new Turtle("Franklin", "Male", currentPlayer, "adult");
        Bear winny = new Bear("Winny", "Male", currentPlayer, "adult");*/

        clientKaZoo.addPlayer(currentPlayer);
        currentPlayer.addOrUpdateAnimal(pigloo);
        currentPlayer.addOrUpdateAnimal(franklin);
        currentPlayer.addOrUpdateAnimal(winny);

        /*
         *       JAVAFX INIT
         */

        // Base group that contains all our nodes (all scene objects)
        Group root = new Group();

        // Setting the main Scene
        Scene zooScene = new Scene(root);
        window.setScene(zooScene);

        // Creating Canvas
        int mapSide = 500;
        Canvas zooCanvas = new Canvas(mapSide, mapSide);

        // Linking canvas to our root
        root.getChildren().add(zooCanvas);

        // Object to draw inputStream the Canvas
        GraphicsContext gc = zooCanvas.getGraphicsContext2D();

        /*
         *          NETWORK INITIALISATION
         */

        clientSocket = new Socket("localhost", PORT);
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());

        sendPlayerToServer();

        final long startNanoTime = System.nanoTime();

        //
        //
        /***
         * Main loop used to draw on the canvas avery frame
         * Also used to update the local zoo of the client from the server
         * First receives information then we display it
         * Then animates the local zoo
         * And finally sends the updated information to the server
         */
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                double t = (currentNanoTime - startNanoTime) / 10000000000.0;
                gc.drawImage(map, 0, 0);

                try {
                    receivedZooFromServer();
                    display(gc);
                    currentPlayer.moveAnimals(clientKaZoo.getObstaclesInZoo(), clientKaZoo.getAnimalsInZoo());
                    displayObstacles(gc);
                    sendPlayerToServer();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        window.show();
    }

    /***
     * Called every frame sends the player information to the server
     * @throws IOException
     */
    public void sendPlayerToServer() throws IOException {
        //System.out.println("Sending player...");
        outputStream.reset();
        outputStream.writeObject(currentPlayer);
        outputStream.flush();
    }

    /***
     * Called to receive information : Zoo from the server
     * Then syncs the players and the animals
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void receivedZooFromServer() throws IOException, ClassNotFoundException {
        //System.out.println("Receiving...");
        // Receiving Zoos
        Zoo zooFromServer = (Zoo) inputStream.readObject();

        clientKaZoo = zooFromServer;

        if (clientKaZoo.getPlayersInZoo().containsKey(currentPlayer.getName())) {
            currentPlayer.setColor(clientKaZoo.getPlayersInZoo().get(currentPlayer.getName()).getColor());
        }
        // Merging Zoo
        // clientKaZoo.syncPlayer(zooFromServer);
        // clientKaZoo.syncAnimals(zooFromServer);
    }

    /***
     * Called when the window closed
     * Closes the streams and the client
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void stop() throws IOException, InterruptedException {
        System.out.println("Closing client");

        sleep(1000);

        // TODO : close streams and socket
    }

    /***
     * Displays a list of animals on the canvas
     * @param animalsToDisplay
     * @param gc
     */
    public void displayAnimals(HashMap<String, Animal> animalsToDisplay, GraphicsContext gc) {
        for (String animalName : animalsToDisplay.keySet()) {
            Animal animal = animalsToDisplay.get(animalName);
            Player player = clientKaZoo.getPlayersInZoo().get(animal.getOwner().getName());

            animal.render(gc, selectImgColor(animal, player.getColor()));
            displayAnimals(animal.getBabies(), gc);
        }
    }

    /***
     * Displays obstacles on the canvas
     * @param gc
     */
    public void displayObstacles(GraphicsContext gc) {
        gc.drawImage(new Image("resources/img/lightTree.png"), 102, 289);
        gc.drawImage(new Image("resources/img/darkTree.png"), 332, 32);
        gc.drawImage(new Image("resources/img/rocks.png"), 410, 283);
    }

    /***
     * Display all animals on teh canvas
     * @param gc
     */
    public void display(GraphicsContext gc) {
        for (String playerName : clientKaZoo.getPlayersInZoo().keySet()) {
            displayAnimals(clientKaZoo.getPlayersInZoo().get(playerName).getPlayerAnimals(), gc);
        }
    }

    /***
     * Allows to select the color of an animal
     * The color parameters allows to select the right image with the corresponding color
     * @param color
     * @return
     */
    public Image selectImgColor(Animal anAnimal, int color) {
        return new Image(anAnimal.getImageUrl() + color + ".png");
    }
}

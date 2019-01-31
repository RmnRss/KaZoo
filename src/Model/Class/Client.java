package Model.Class;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Animals.Bear;
import Model.Class.Zoo.Animals.Penguin;
import Model.Class.Zoo.Animals.Turtle;
import Model.Class.Zoo.Zoo;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.*;
import java.net.Socket;

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

    private Image imgTurtle = new Image("resources/img/circle.png");
    private Image imgPenguin = new Image("resources/img/rectangle.png");
    private Image imgBear = new Image("resources/img/triangle.png");

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
        // Side of a square inputStream px
        int mapSide = 500 ;
        window.setTitle("clientKaZoo");

        name = "Michel";
        Penguin pigloo = new Penguin("Pig","Bonhomme", this);
        Turtle franklin = new Turtle("Fran", "Bro", this);
        Bear winny = new Bear("Win", "Fragile", this);


        /*name = "Didier";
        Penguin pigloo = new Penguin("Pigloo","Bonhomme", this);
        Turtle franklin = new Turtle("Franklin", "Bro", this);
        Bear winny = new Bear("Winny", "Fragile", this);*/

        /*name = "Thierry";
        Penguin pigloo = new Penguin("vdvqsdcs","Bonhomme", this);
        Turtle franklin = new Turtle("fefq", "Bro", this);
        Bear winny = new Bear("erzgrz", "Fragile", this);*/

        clientKaZoo.addAnimal(pigloo);
        clientKaZoo.addAnimal(franklin);
        clientKaZoo.addAnimal(winny);

        // Base group that contains all our nodes (all scene objects)
        Group root = new Group();

        //Setting the main Scene
        Scene zooScene = new Scene(root);
        window.setScene(zooScene);

        //Creating Canvas
        Canvas zooCanvas = new Canvas(mapSide, mapSide);

        //Linking canvas to our root
        root.getChildren().add(zooCanvas);

        // Object to draw inputStream the Canvas
        GraphicsContext gc = zooCanvas.getGraphicsContext2D();

        // Drawing images
        // 0,0 (top left corner)

        gc.drawImage(map,0 ,0 );
        franklin.render(gc, imgTurtle);
        winny.render(gc, imgBear);
        pigloo.render(gc,imgPenguin);

        // Other way to draw
        // gc.drawImage(pigloo.getSprite().getImage(), 0,0 , pigloo.getSprite().getWidth(), pigloo.getSprite().getHeight(), pigloo.getSprite().getX(), pigloo.getSprite().getY(), pigloo.getSprite().getWidth(), pigloo.getSprite().getHeight());

        // Initializing time
        final long startNanoTime = System.nanoTime();

        clientSocket = new Socket("localhost", PORT);
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());

        // Starting animating images
        // Drawing loops
        // Also used to received and send information
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                //TO DO : Make this part dynamic
                double t = (currentNanoTime - startNanoTime) / 10000000000.0;
                gc.drawImage(map , 0,0 );

                System.out.println("Size clientZoo :" + clientKaZoo.getAnimalsInZoo().size());
                try {

                    sendInfoToServer();
                    receiveInfoFromServer();
                    displayAnimals(gc);

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
     * Called to receive information
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void receiveInfoFromServer() throws IOException, ClassNotFoundException, InterruptedException
    {
        System.out.println("Receiving...");
        // Receiving Zoos
        Zoo zooFromServer = (Zoo) inputStream.readObject();

        // Merging Zoo
        clientKaZoo.syncAnimals(zooFromServer);
    }

    @FXML
    public void exitApplication(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @Override
    public void stop() throws IOException, InterruptedException
    {
        System.out.println("Closing");

        // TO DO:
        // Send shutdown message to server
        sleep(1000);
    }

    public void displayAnimals(GraphicsContext gc)
    {
        for (String animalName : clientKaZoo.getAnimalsInZoo().keySet())
        {
            Animal animal = clientKaZoo.getAnimalsInZoo().get(animalName);

            if(animal instanceof Bear){
                animal.render(gc, imgBear);
            }
            else
            {
                if(animal instanceof Penguin)
                {
                    animal.render(gc, imgPenguin);
                }
                else
                {
                    animal.render(gc, imgTurtle);
                }
            }
        }

    }

    //-- Getter and Setter --//

    public String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }
}

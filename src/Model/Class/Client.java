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

/***
 * Client class Runs the interface and connects to the server
 * Every client as its own zoo
 */
public class Client extends Application
{
    public static final int PORT = 6789;

    private String ipRomain = "192.168.43.92";
    private String ipSandra = "192.168.43.106";

    private Socket clientSocket = new Socket("localhost", PORT);
    //private Socket clientSocket = new Socket(ipRomain, PORT);

    private Zoo clientKaZoo = new Zoo();
    private String name;

    // Images declaration
    private Image map = new Image("resources/img/map.png");

    private Image imgTurtle = new Image("resources/img/circle.png");
    private Image imgPenguin = new Image("resources/img/rectangle.png");
    private Image imgBear = new Image("resources/img/triangle.png");

    private ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
    private ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());


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
        Penguin pigloo = new Penguin("Pig","Bonhomme");
        Turtle franklin = new Turtle("Fran", "Bro");
        Bear winny = new Bear("Win", "Fragile");

        /*
        name = "Didier";
        Penguin pigloo = new Penguin("Pigloo","Bonhomme");
        Turtle franklin = new Turtle("Franklin", "Bro");
        Bear winny = new Bear("Winny", "Fragile");
        */

        /*
        name = "Thierry";
        Penguin pigloo = new Penguin("vdvqsdcs","Bonhomme");
        Turtle franklin = new Turtle("fefq", "Bro");
        Bear winny = new Bear("erzgrz", "Fragile");
        */

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

        // Starting animating images
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                //TO DO : Make this part dynamic
                double t = (currentNanoTime - startNanoTime) / 10000000000.0;
                gc.drawImage(map , 0,0 );

                System.out.println("Size clientZoo :" + clientKaZoo.getAnimalsInZoo().size());
                try {
                    receiveInfoFromServer();
                    displayAnimals(gc);
                    moveAnimals(gc);
                    sendInfoToServer();

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
     * Called every frame sends information about the zoo to the server
     * @throws IOException
     */
    public void sendInfoToServer() throws IOException {
        System.out.println("Sending...");
        outputStream.writeObject(clientKaZoo);
        outputStream.flush();
        System.out.println("Sent : " + clientKaZoo.getAnimalsInZoo().size());
    }

    /***
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void receiveInfoFromServer() throws IOException, ClassNotFoundException {

        System.out.println("Receiving...");

        Zoo tempZoo = (Zoo) inputStream.readObject();

        System.out.println("Received : " + tempZoo.getAnimalsInZoo().size());

        for (String animalName : tempZoo.getAnimalsInZoo().keySet()) {
            clientKaZoo.addAnimal(tempZoo.getAnimalsInZoo().get(animalName));
        }
        /*
        clientKaZoo = (Zoo)inputStream.readObject();*/
    }

    @FXML
    public void exitApplication(ActionEvent event) throws IOException {
        Platform.exit();
    }

    @Override
    public void stop() throws IOException, InterruptedException {
        System.out.println("Closing");
        PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        out.write("Exit");
        out.flush();
        Thread.sleep(5000);
    }

    /***
     * Refreshes position then displays animals
     * @param gc
     */
    public void moveAnimals(GraphicsContext gc){
        for (String animalName : clientKaZoo.getAnimalsInZoo().keySet()) {
            Animal animal = clientKaZoo.getAnimalsInZoo().get(animalName);
            if(!animal.isArrived())
            {
                animal.move(0.5);

                int i = 0;
                Boolean isObstacle = false;

                while(i < clientKaZoo.getObstaclesInZoo().size() && !isObstacle)
                {
                    if(animal.intersects(clientKaZoo.getObstaclesInZoo().get(i).getPosition()))
                    {
                        isObstacle = true;
                    }
                    else
                    {
                        i++;
                    }
                }
                if(isObstacle)
                {
                    animal.setTarget();
                }
            }
            else
            {
                animal.setTarget();
            }
        }
    }

    public void displayAnimals(GraphicsContext gc){
        for (String animalName : clientKaZoo.getAnimalsInZoo().keySet()) {
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

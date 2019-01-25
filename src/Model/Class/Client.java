package Model.Class;

import Model.Class.Zoo.Animals.Animal;
import Model.Class.Zoo.Animals.Bear;
import Model.Class.Zoo.Animals.Penguin;
import Model.Class.Zoo.Animals.Turtle;
import Model.Class.Zoo.Zoo;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.EventListener;

/***
 * Client class Runs the interface and connects to the server
 * Every client as its own zoo
 */
public class Client extends Application
{
    public static final int PORT = 6789;

    private Socket clientSocket = new Socket("localhost", PORT);
    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

    private Zoo clientKaZoo = new Zoo();
    private String name;

    // Images declaration
    private Image map = new Image("resources/img/map.png");

    private Image imgTurtle = new Image("resources/img/circle.png");
    private Image imgPenguin = new Image("resources/img/rectangle.png");
    private Image imgBear = new Image("resources/img/triangle.png");

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
        // Side of a square in px
        int mapSide = 500 ;
        window.setTitle("clientKaZoo");

        Penguin pigloo = new Penguin("Pigloo","Bonhomme");
        Turtle franklin = new Turtle("Franklin", "Bro");
        Bear winny = new Bear("Winny", "Fragile");

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

        // Object to draw in the Canvas
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
                moveAnimals(gc);
                System.out.println(clientKaZoo.getAnimalsInZoo().size());
                try {
                    sendInfoToServer();
                    receiveInfoFromServer();
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
        out.writeObject(clientKaZoo);
        out.flush();
    }

    public void receiveInfoFromServer() throws IOException, ClassNotFoundException {
        Zoo tempZoo = (Zoo)in.readObject();
        for (String animalName : tempZoo.getAnimalsInZoo().keySet()) {
            clientKaZoo.addAnimal(tempZoo.getAnimalsInZoo().get(animalName));
        }
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

            if(animal instanceof Bear){
                System.out.println("Bear");
                animal.render(gc, imgBear);
            }
            else
            {
                if(animal instanceof Penguin)
                {
                    System.out.println("Penguin");
                    animal.render(gc, imgPenguin);
                }
                else
                {
                    System.out.println("Turtle");
                    animal.render(gc, imgTurtle);
                }
            }
        }
    }

    //-- Getter and Setter --//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package Model.Class;

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

/***
 * Client class Runs the interface and connects to the server
 * Every client as its own zoo
 */
public class Client extends Application
{
    public static final int PORT = 6789;
    private Socket clientSocket = new Socket("192.168.43.92", PORT);
    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

    private Zoo clientKaZoo = new Zoo();
    private String name;

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

        // Images declaration
        Image map = new Image("resources/img/map.png");

        Image imgTurtle = new Image("resources/img/circle.png");
        Image imgPenguin = new Image("resources/img/rectangle.png");
        Image imgBear = new Image("resources/img/triangle.png");

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
                if(!pigloo.isArrived())
                {
                    double t = (currentNanoTime - startNanoTime) / 10000000000.0;

                    pigloo.move(t);

                    int i = 0;
                    Boolean isObstacle = false;

                    while(i < clientKaZoo.getObstaclesInZoo().size() && !isObstacle)
                    {
                        if(pigloo.intersects(clientKaZoo.getObstaclesInZoo().get(i).getPosition()))
                        {
                            isObstacle = true;
                            System.out.println(isObstacle);
                        }
                        else
                        {
                            i++;
                        }
                    }
                    if(isObstacle)
                    {
                        pigloo.setTarget();
                    }

                    System.out.println(t);
                    gc.drawImage(map , 0,0 );
                    pigloo.render(gc, imgPenguin);
                    franklin.render(gc, imgTurtle);

                }
                else
                {
                    pigloo.setTarget();
                }

                try {
                    sendInfoToServer();
                } catch (IOException e) {
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

    //-- Getter and Setter --//

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

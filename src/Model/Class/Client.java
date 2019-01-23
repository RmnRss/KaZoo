package Model.Class;

import Model.Class.Animals.Penguin;
import Model.Class.Animals.Turtle;
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

public class Client extends Application {
    public static final int PORT = 6789;
    private Socket clientSocket = new Socket("localhost", PORT);
    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
    private Zoo clientKaZoo;

    public Client() throws IOException {
    }

    public static void main(String args[]) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        // SIde of a square in px
        int mapSide = 500 ;
        window.setTitle("clientKaZoo");

        // Image declaration
        Image map = new Image("resources/img/map.png");
        /*
        Image pigloo = new Image("resources/img/penguin.png");
        Image baloo = new Image("resources/img/babyBear.png");
        Image caro = new Image("resources/img/turtle.png");
        */

        Penguin pigloo = new Penguin("Pigloo","Bonhomme");
        Turtle caro = new Turtle("Caroline", "Madame");

        clientKaZoo.addAnimal(pigloo);
        clientKaZoo.addAnimal(caro);

        // Base group that contains all our nodes (all scene objects)
        Group root = new Group();

        Scene zooScene = new Scene(root);
        window.setScene(zooScene);

        Canvas zooCanvas = new Canvas(mapSide, mapSide);

        root.getChildren().add(zooCanvas);

        // Object to draw in the Canvas
        GraphicsContext gc = zooCanvas.getGraphicsContext2D();

        // Draw an Image in 0,0 (top left corner)
        gc.drawImage(map,0 ,0 );


        gc.drawImage(caro.getSprite().getImage(), 0,0 , caro.getSprite().getWidth(), caro.getSprite().getHeight(), caro.getSprite().getPositionX(), caro.getSprite().getPositionY(), caro.getSprite().getWidth(), caro.getSprite().getHeight());
        //gc.drawImage(pigloo.getSprite().getImage(), 0,0 , pigloo.getSprite().getWidth(), pigloo.getSprite().getHeight(), pigloo.getSprite().getPositionX(), pigloo.getSprite().getPositionY(), pigloo.getSprite().getWidth(), pigloo.getSprite().getHeight());

        final long startNanoTime = System.nanoTime();

        // Deplacements
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                if(!pigloo.getSprite().isArrived())
                {
                    double t = (currentNanoTime - startNanoTime) / 10000000000.0;

                    if (t > 0.15)
                    {
                        t = 0.15;
                    }else{
                        t = (currentNanoTime - startNanoTime) / 10000000000.0;
                    }

                    pigloo.getSprite().update(t);

                    int i = 0;
                    Boolean isObstacle = false;

                    while(i < clientKaZoo.getObstaclesInZoo().size() && !isObstacle)
                    {
                        if(pigloo.getSprite().intersects(clientKaZoo.getObstaclesInZoo().get(i).getSprite()))
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
                        pigloo.getSprite().setTarget();
                    }

                    System.out.println(t);
                    gc.drawImage(map , 0,0 );
                    pigloo.getSprite().render(gc);
                    caro.getSprite().render(gc);

                }
                else
                {
                    pigloo.getSprite().setTarget();
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

    public void sendInfoToServer() throws IOException {
        out.writeObject(clientKaZoo);
        out.flush();
    }
}

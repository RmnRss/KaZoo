package sample;

import Model.Class.Animations.AnimatedImage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception
    {
       /* Parent root = FXMLLoader.load(getClass().getResource("../View/window.fxml"));
        window.setTitle("KaZoo");
        window.setScene(new Scene(root, 300, 275));
        window.show();*/

       // SIde of a square in px
        int mapSide = 500 ;

        // Image declaration
        Image grass = new Image("resources/img/grass.jpg");
        Image pigloo = new Image("resources/img/penguin.png");
        Image baloo = new Image("resources/img/babyBear.png");
        Image caro = new Image("resources/img/turtle.png");


        window.setTitle("KaZoo");

        // Base group that contains all our nodes (all scene objects)
        Group root = new Group();

        Scene zooScene = new Scene(root);
        window.setScene(zooScene);

        Canvas zooCanvas = new Canvas(mapSide, mapSide);

        root.getChildren().add(zooCanvas);

        // Object to draw in the Canvas
        GraphicsContext gc = zooCanvas.getGraphicsContext2D();

        // Draw an Image in 0,0 (top left corner)
        gc.drawImage(grass,0 ,0 );


        gc.drawImage(pigloo, 0,0 , 31, 35, 0,0 , 31, 35);

        final long startNanoTime = System.nanoTime();

        // Deplacements
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                double x = 100 + 128 * Math.cos(t);
                double y = 100 + 128 * Math.sin(t);

                gc.drawImage(grass , 0,0 );
                gc.drawImage(pigloo, 0,0 , 31, 35, x,y , 31, 35);

            }
        }.start();

        window.show();

    }


    public static void main(String[] args)
    {
        launch(args);
    }
}

package sample;

import Model.Class.Animals.Penguin;
import Model.Class.Animals.Turtle;
import Model.Class.Animations.AnimatedImage;
import Model.Class.Zoo.Zoo;
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

        Zoo KaZoo = new Zoo();


        // Image declaration
        Image grass = new Image("resources/img/map.png");
        /*
        Image pigloo = new Image("resources/img/penguin.png");
        Image baloo = new Image("resources/img/babyBear.png");
        Image caro = new Image("resources/img/turtle.png");
        */

        Penguin pigloo = new Penguin("Pigloo","Bonhomme");
        Turtle caro = new Turtle("Caroline", "Madame");

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


        gc.drawImage(caro.getSprite().getImage(), 0,0 , caro.getSprite().getWidth(), caro.getSprite().getHeight(), caro.getSprite().getPositionX(), caro.getSprite().getPositionY(), caro.getSprite().getWidth(), caro.getSprite().getHeight());
        //gc.drawImage(pigloo.getSprite().getImage(), 0,0 , pigloo.getSprite().getWidth(), pigloo.getSprite().getHeight(), pigloo.getSprite().getPositionX(), pigloo.getSprite().getPositionY(), pigloo.getSprite().getWidth(), pigloo.getSprite().getHeight());

        System.out.println(KaZoo.getObstaclesInZoo().get(0).getSprite().intersects(caro.getSprite()));
        System.out.println(caro.getSprite().getBoundary().getMinX());
        final long startNanoTime = System.nanoTime();

        // Deplacements
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                if(!pigloo.getSprite().isArrived())
                {
                    double t = (currentNanoTime - startNanoTime) / 10000000000.0;

                    pigloo.getSprite().update(t);

                    int i = 0;
                    Boolean isObstacle = false;

                    while(i < KaZoo.getObstaclesInZoo().size() && !isObstacle)
                    {
                        if(pigloo.getSprite().intersects(KaZoo.getObstaclesInZoo().get(i).getSprite()))
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

                    System.out.println(currentNanoTime);
                    gc.drawImage(grass , 0,0 );
                    pigloo.getSprite().render(gc);
                    caro.getSprite().render(gc);

                }
                else
                {
                    pigloo.getSprite().setTarget();
                }
            }
        }.start();


        window.show();

    }


    public static void main(String[] args)
    {
        launch(args);
    }
}

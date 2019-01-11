package Model.Class.Animations;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Sprite
{
    private Image image;
    private int positionX;
    private int positionY;
    private Rectangle2D target;
    private int velocityX;
    private int velocityY;
    private int width;
    private int height;

    // Animal sprite constructor
    public Sprite(int velocity, String url)
    {
        Random r = new Random();
        int mapSide = 500;

        positionX = r.nextInt(mapSide);
        positionY = r.nextInt(mapSide);

        velocityX = velocity;
        velocityY = velocity;

        target = new Rectangle2D(r.nextInt(mapSide), r.nextInt(mapSide), 5, 5);
        image = new Image(url);
        width = 30;
        height = 30;
    }

    // Obstacle sprite constructor
    public Sprite(int positionX, int positionY, int width, int height, String url){
        this.positionX = positionX;
        this.positionY = positionY;

        this.velocityX = 0;
        this.velocityY = 0;

        this.width = width;
        this.height = height;

        this.image = new Image(url);
    }



    public void setImage(Image i)
    {
        image = i;
        width = (int)i.getWidth();
        height = (int)i.getHeight();
    }

    public void setImage(String filename)
    {
        Image i = new Image(filename);
        setImage(i);
    }

    public void setPosition(int x, int y)
    {
        positionX = x;
        positionY = y;
    }

    public void setVelocity(int x, int y)
    {
        velocityX = x;
        velocityY = y;
    }

    public void addVelocity(int x, int y)
    {
        velocityX += x;
        velocityY += y;
    }

    public void update(double time)
    {
        int deltaPosX, deltaPosY;
        deltaPosX = (int)target.getMinX() - positionX;
        deltaPosY = (int)target.getMinY() - positionY;

        // Déplacement vers la droite
        if(deltaPosX > 0){
            positionX += velocityX * time;
        }
        else // Déplacement vers la gauche
        {
            positionX -= velocityX * time;
        }

        // Déplacement vers le bas
        if(deltaPosY > 0){
            positionY += velocityY * time;
        }
        else // Déplacement vers la haut
        {
            positionY -= velocityY * time;
        }
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage(image, positionX, positionY);
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects(this.getBoundary());
    }

    public boolean intersects(Rectangle2D target)
    {
        return target.intersects(this.getBoundary());
    }

    public String toString()
    {
        return " Position: [" + positionX + "," + positionY + "]"
                + " Velocity: [" + velocityX + "," + velocityY + "]";
    }

    public Image getImage() {
        return image;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public Rectangle2D getTarget() {
        return target;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public int getWidth(){  return this.width; }

    public int getHeight(){ return this.height; }

    public void setTarget(){
        int mapSide = 500;

        Random r = new Random();

        target = new Rectangle2D(r.nextInt(mapSide), r.nextInt(mapSide), 5, 5);
    }

    public Boolean isArrived(){
        return this.intersects(this.target);
    }
}
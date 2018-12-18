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
    private int targetX;
    private int targetY;
    private int velocityX;
    private int velocityY;
    private int width;
    private int height;

    public Sprite(int velocity, String url)
    {
        Random r = new Random();
        int mapSide = 500;

        positionX = r.nextInt(mapSide);
        positionY = r.nextInt(mapSide);

        velocityX = velocity;
        velocityY = velocity;

        image = new Image(url);
        width = 31;
        height = 35;
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
        positionX += velocityX * time;
        positionY += velocityY * time;
    }

    public void render(GraphicsContext gc)
    {
        gc.drawImage( image, positionX, positionY );
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(positionX,positionY,width,height);
    }

    public boolean intersects(Sprite s)
    {
        return s.getBoundary().intersects( this.getBoundary() );
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

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTarget(){
        int mapSide = 500;

        Random r = new Random();

        targetX = r.nextInt(mapSide);
        targetY = r.nextInt(mapSide);
    }

    public Boolean isArrived(){
        if(this.positionX == this.targetX && this.positionY == this.targetY)
            return true;
        else return false;
    }
}
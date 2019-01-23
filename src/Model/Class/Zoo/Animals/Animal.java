package Model.Class.Zoo.Animals;

import Model.Class.Animations.Position;
import Model.Class.Animations.Velocity;
import Model.Class.Client;
import Model.Class.Zoo.Obstacle;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.Random;
import javafx.scene.image.Image;

public abstract class Animal implements Serializable
{
    protected int name;

    protected int age;

    protected Client owner;

    protected String sex;
    protected float averageSize;
    protected float size;

    protected int litter;

    protected Animal father;
    protected Animal mother;

    protected Position position;

    protected Velocity velocity;

    protected Rectangle2D target;


    public Position getPosition(){ return this.position; }

    public Rectangle2D getTarget() {
        return target;
    }

    public void setTarget(Rectangle2D target) {
        this.target = target;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    /***
     * Function
     * Handles the growth of an Animal
     * Changing size and sprites depending on its age
     * ***/
    abstract void growth(int Age);

    public void move(double time)
    {
        int deltaPosX, deltaPosY;
        deltaPosX = (int)target.getMinX() - position.getX();
        deltaPosY = (int)target.getMinY() - position.getY();

        // Déplacement vers la droite
        if(deltaPosX > 0){
            position.setX((int)(position.getX() + (velocity.getVelocityX() * time)));
        }
        else // Déplacement vers la gauche
        {
            position.setX((int)(position.getX() - (velocity.getVelocityX() * time)));
        }

        // Déplacement vers le bas
        if(deltaPosY > 0){
            position.setY((int)(position.getY() + (velocity.getVelocityY() * time)));
        }
        else // Déplacement vers la haut
        {
            position.setY((int)(position.getY() - (velocity.getVelocityY() * time)));
        }
    }

    public void render(GraphicsContext gc, Image theImage)
    {
        gc.drawImage(theImage, position.getX(), position.getY());
    }

    public Rectangle2D getBoundary()
    {
        return new Rectangle2D(position.getX(), position.getY(), size, size);
    }


    public boolean intersects(Obstacle obstacle)
    {

        return obstacle.getBoundary().intersects(this.getBoundary());
    }


    public boolean intersects(Rectangle2D target)
    {
        return target.intersects(this.getBoundary());
    }

    public void setTarget(){
        int mapSide = 500;

        Random r = new Random();

        target = new Rectangle2D(r.nextInt(mapSide), r.nextInt(mapSide), 5, 5);
    }

    public Boolean isArrived(){
        return this.intersects(this.target);
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getAverageSize() {
        return averageSize;
    }

    public void setAverageSize(float averageSize) {
        this.averageSize = averageSize;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public int getLitter() {
        return litter;
    }

    public void setLitter(int litter) {
        this.litter = litter;
    }

    public Animal getFather() {
        return father;
    }

    public void setFather(Animal father) {
        this.father = father;
    }

    public Animal getMother() {
        return mother;
    }

    public void setMother(Animal mother) {
        this.mother = mother;
    }

}

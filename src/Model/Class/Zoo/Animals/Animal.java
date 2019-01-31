package Model.Class.Zoo.Animals;

import Model.Class.Animations.Position;
import Model.Class.Animations.Velocity;
import Model.Class.Client;
import javafx.geometry.VerticalDirection;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.Random;
import javafx.scene.image.Image;

/***
 * Represents an animal in a Zoo
 */
public abstract class Animal implements Serializable
{
    protected String name;

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

    protected Position target;


    /***
     * Function
     * Handles the growth of an Animal
     * Changing size and sprites depending on its age
     * ***/
    abstract void growth(int Age);

    /***
     * Handles the movements of an animal
     * @param time
     */
    public void move(double time)
    {
        // TO DO : Smooth vertical and horizontal movements
        int deltaPosX, deltaPosY;
        deltaPosX = (int)target.getX() - position.getX();
        deltaPosY = (int)target.getY() - position.getY();

        //Horizontal movement
        if(deltaPosX == 0)
        {
            // X position stays the same
        }else{
            if(deltaPosX > 0){
                // Move to the right
                position.setX((int)(position.getX() + (velocity.getVelocityX() * time)));
            }
            else
            {
                // Move to the left
                position.setX((int)(position.getX() - (velocity.getVelocityX() * time)));
            }
        }


        if(deltaPosY == 0)
        {
            // Y position stays the same
        }else{

            if(deltaPosY > 0){
                // Move downwards
                position.setY((int)(position.getY() + (velocity.getVelocityY() * time)));
            }
            else
            {
                // Move upwards
                position.setY((int)(position.getY() - (velocity.getVelocityY() * time)));
            }
        }

    }

    /***
     * Easier way to draw an animal on the canvas
     * @param gc
     * @param theImage
     */
    public void render(GraphicsContext gc, Image theImage)
    {
        gc.drawImage(theImage, position.getX(), position.getY());
    }

    /***
     * Checks if the position of an animal is close to its target
     * If the animal is in a 5 pixel radius of his target then it returns true
     * @param target
     * @return
     */
    public boolean intersects(Position target)
    {
        Boolean inX = position.getX() > (target.getX() - 5) && position.getX() < (target.getX() + 5);
        Boolean inY = position.getY() > (target.getY() - 5) && position.getY() < (target.getY() + 5);

        if(inX && inY) return true;
        else return false;
    }

    /***
     * Sets a Random target fot the animal
     */
    public void setTarget(){
        int mapSide = 500;

        Random r = new Random();

        target = new Position(r.nextInt(mapSide), r.nextInt(mapSide));
    }

    /***
     * Uses Interects method to know if an animal reached its target
     * @return
     */
    public Boolean isArrived(){
        return this.intersects(this.target);
    }

    //-- Getter and Setter --//

    public Position getPosition(){ return this.position; }

    public Position getTarget() {
        return target;
    }

    public void setTarget(int newX, int newY) {

        this.target.setX(newX);
        this.target.setY(newY);
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
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

package Model.Class.Zoo.Animals;

import Model.Class.Animations.Position;
import Model.Class.Animations.Velocity;
import Model.Class.Client;
import Model.Class.Network.TempReproduction;
import Model.Class.Zoo.Obstacle;
import javafx.geometry.VerticalDirection;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import javafx.scene.image.Image;

/***
 * Represents an animal in a Zoo
 */
public abstract class Animal implements Serializable
{
    protected String name;

    protected int age;

    protected String owner;

    protected String sex;
    protected float averageSize;
    protected float size;

    protected int litter;
    protected HashMap<String, Animal> babies;

    protected Animal father;
    protected Animal mother;

    protected Position position;

    protected Velocity velocity;

    protected Position target;

    protected Boolean canHaveBabies;


    /***
     * Function
     * Handles the growth of an Animal
     * Changing size and sprites depending on its age
     * ***/
    abstract void growth(int Age);

    @Override
    public abstract String toString();

    /***
     * Handles the movements of an animal
     * @param time
     */
    public synchronized void move(double time)
    {
        double deltaPosX, deltaPosY;
        deltaPosX = target.getX() - position.getX();
        deltaPosY = target.getY() - position.getY();

        //Horizontal movement
        if(deltaPosX >= -1 && deltaPosX <= 1)
        {
            // X position stays the same
        }else{
            if(deltaPosX > 0){
                // Move to the right
                position.setX((position.getX() + (velocity.getVelocityX() * time)));
            }
            else
            {
                // Move to the left
                position.setX((position.getX() - (velocity.getVelocityX() * time)));
            }
        }


        if(deltaPosY >= -1 && deltaPosY <= 1)
        {
            // Y position stays the same
        }else{

            if(deltaPosY > 0){
                // Move downwards
                position.setY((position.getY() + (velocity.getVelocityY() * time)));
            }
            else
            {
                // Move upwards
                position.setY((position.getY() - (velocity.getVelocityY() * time)));
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
    public synchronized boolean intersects(Position target)
    {
        double range = 25;
        Boolean inX = position.getX() > (target.getX() - range) && position.getX() < (target.getX() + range);
        Boolean inY = position.getY() > (target.getY() - range) && position.getY() < (target.getY() + range);

        if(inX && inY) return true;
        else return false;
    }

    /***
     * Checks if the position of an animal is close to the obstacle
     * @param anObstacle
     * @return
     */
    public synchronized boolean hitObstacle(Obstacle anObstacle)
    {
        double width = anObstacle.getWidth();
        double height = anObstacle.getHeight();

        Boolean inX = position.getX() > anObstacle.getPosition().getX() && position.getX() < (anObstacle.getPosition().getX() + width)  || position.getX() + size > anObstacle.getPosition().getX() && position.getX() < (anObstacle.getPosition().getX() + width);
        Boolean inY = position.getY() > anObstacle.getPosition().getY() && position.getY() < (anObstacle.getPosition().getY() + height) || position.getY() + size > anObstacle.getPosition().getY() && position.getY() < (anObstacle.getPosition().getY() + height);

        if(inX && inY) return true;
        else return false;
    }

    /***
     * Sets a Random target fot the animal
     */
    public synchronized void setTarget(){
        int mapSide = 500;

        Random r = new Random();

        target = new Position(r.nextInt(mapSide), r.nextInt(mapSide));
    }

    /***
     * Uses intersect method to know if an animal reached its target
     * @return
     */
    public synchronized Boolean isArrived(){
        return this.intersects(this.target);
    }

    //-- Getter and Setter --//

    public Position getPosition(){ return this.position; }

    public Position getTarget() {
        return target;
    }

    public synchronized void setTarget(double newX, double newY) {

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
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

    public HashMap<String, Animal> getBabies() {
        return babies;
    }

    public synchronized void setBabies(HashMap<String, Animal> babies) {
        this.babies = babies;
    }

    public Boolean getCanHaveBabies() {
        return canHaveBabies;
    }

    public synchronized void setCanHaveBabies(Boolean CanOrNotCanHaveBabies) {
        this.canHaveBabies = CanOrNotCanHaveBabies;
    }

    public Boolean isAnAdult(){
        return this.getAge() > 2;
    }

    /***
     * Makes an animal reproduce with another one on some conditions
     * Disable reproduction for a while after coitus
     * Creates a random number of baby animals
     * @param otherAnimal
     */
    public abstract void haveBabiesWith(Animal otherAnimal);

    public synchronized void mating(HashMap<String, Animal> animalsInZoo){
        Boolean hadBabies = false;

        Iterator<Map.Entry<String, Animal>> iterator = animalsInZoo.entrySet().iterator();
        while(iterator.hasNext() && !hadBabies)
        {
            Map.Entry<String, Animal> entry = iterator.next();
            Animal otherAnimal = animalsInZoo.get(entry.getKey());
            if(otherAnimal != this && this.isAnAdult() && this.getCanHaveBabies()) {
                if (this.intersects(otherAnimal.getPosition())) {
                    if (otherAnimal.getClass().getName().equals(this.getClass().getName()) && !this.getSex().equals(otherAnimal.getSex())) {
                        this.haveBabiesWith(otherAnimal);
                        hadBabies = true;
                    }
                }
            }
        }
    }


}

package Model.Class.Zoo.Animals;

import Model.Class.Animations.Position;
import Model.Class.Animations.Velocity;
import Model.Class.Client;
import javafx.geometry.VerticalDirection;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
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
    public synchronized boolean intersects(Position target)
    {
        int range = 20;
        Boolean inX = position.getX() > (target.getX() - range) && position.getX() < (target.getX() + range);
        Boolean inY = position.getY() > (target.getY() - range) && position.getY() < (target.getY() + range);

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
     * Uses Interects method to know if an animal reached its target
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

    public synchronized void setTarget(int newX, int newY) {

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

    public synchronized void setCanHaveBabies() {
        this.canHaveBabies = !canHaveBabies;
    }

    public Boolean isAnAdult(){
        return this.getAge() > 2;
    }

    public synchronized void haveBabiesWith(Animal otherAnimal){
        byte[] array = new byte[10]; // length is bounded by 7
        new Random().nextBytes(array);
        String name = new String(array, Charset.forName("UTF-8"));
        String[] sexes = {"Male", "Female"};
        Random random = new Random();

        if (this.getSex().equals("Female")) {
            System.out.println("Coucou");
            this.getBabies().put(name, new Bear(name, sexes[random.nextInt(1)], this.getOwner(), otherAnimal, this));
            System.out.println("nb bébés : " + this.getBabies().size());
            this.setCanHaveBabies();
            otherAnimal.setCanHaveBabies();
        }
    }
}

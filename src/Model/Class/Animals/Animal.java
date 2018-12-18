package Model.Class.Animals;

import Model.Class.Animations.Sprite;
import Model.Class.Client;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.util.Random;

public abstract class Animal
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

    protected Sprite sprite;

    /***
     * Function
     * Handles the growth of an Animal
     * Changing size and sprites depending on its age
     * ***/
    abstract void growth(int Age);

    public void move()
    {
        //int targetX = getTargetX();
        //int targetY = getTargetY();



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

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}

package Model.Class.Animations;

import java.io.Serializable;

/***
 * Velocity of an entity in the game
 */
public class Velocity implements Serializable {
    private int velocityX;
    private int velocityY;

    public Velocity(int velocity) {
        this.velocityX = velocity;
        this.velocityY = velocity;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocity(int newVelocity)
    {
        velocityX = newVelocity;
        velocityY = newVelocity;
    }

    public void addVelocity(int velocityToAdd)
    {
        velocityX += velocityToAdd;
        velocityY += velocityToAdd;
    }
}

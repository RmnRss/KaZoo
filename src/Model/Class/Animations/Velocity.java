package Model.Class.Animations;

import java.io.Serializable;

/***
 * Velocity of an entity in the game
 */
public class Velocity implements Serializable {
    private double velocityX;
    private double velocityY;

    public Velocity(double velocity) {
        this.velocityX = velocity;
        this.velocityY = velocity;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public void setVelocity(double newVelocity)
    {
        velocityX = newVelocity;
        velocityY = newVelocity;
    }

    public void addVelocity(double velocityToAdd)
    {
        velocityX += velocityToAdd;
        velocityY += velocityToAdd;
    }
}

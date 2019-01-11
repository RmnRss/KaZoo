package Model.Class.Zoo;

import Model.Class.Animations.Sprite;

public class Obstacle
{
    private Sprite sprite;

    public Obstacle(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}

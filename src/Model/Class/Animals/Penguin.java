package Model.Class.Animals;

import Model.Class.Animations.Sprite;
import javafx.scene.image.Image;

import java.util.Random;

public class Penguin extends Animal
{
    public Penguin()
    {

    }

    public Penguin(String Name, String Sex)
    {
        averageSize = 5;
        litter = 1;
        sprite = new Sprite(5, "resources/img/penguin.png");
    }

    // Baby Penguin Constructor
    public Penguin(String Name, String Sex, Animal Father, Animal Mother)
    {
        this(Name, Sex);
        size = 1;
        father = Father;
        mother = Mother;
        age = 0;

    }

    @Override
    public void growth(int Age)
    {
        Random r = new Random();
        switch (Age)
        {
            //Baby
            case 0:
                //sprite = ;
                break;
            //Child
            case 2:
                size = size + r.nextInt(3-(int) size);
                //sprite = ;
                break;
            //Adult
            case 5:
                size = size + r.nextInt((int)((averageSize+1)-size));
                //sprite = ;
                break;
            //Old
            case 15:
                //sprite = ;
                break;
            //Ded
            case 20:
                //sprite = ;
                break;
        }
    }
}

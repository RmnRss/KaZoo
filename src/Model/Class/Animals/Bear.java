package Model.Class.Animals;

import java.util.Random;

public class Bear extends Animal
{
    public Bear()
    {

    }

    public Bear(String Name, String Sex)
    {
        averageSize = 15;
        speed = 14;
        litter = 2;


    }

    // Baby Bear
    public Bear(String Name, String Sex, Animal Father, Animal Mother)
    {
        this(Name, Sex);
        size = 5;
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
                size = size + r.nextInt(8-(int) size);
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

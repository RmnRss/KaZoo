package Model.Class.Zoo.Animals;

/***
 * Temporizing thread to avoid multiple coitus at a time
 */
public class TempReproduction implements Runnable
{
    private Animal mother;
    private Animal father;

    public TempReproduction(Animal TempoMother, Animal TempoFather){
        mother = TempoMother;
        father = TempoFather;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
            mother.setCanHaveBabies(true);
            father.setCanHaveBabies(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

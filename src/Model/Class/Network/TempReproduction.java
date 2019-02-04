package Model.Class.Network;

import Model.Class.Zoo.Animals.Animal;

public class TempReproduction implements Runnable{
    private Animal mother;
    private Animal father;

    public TempReproduction(Animal TempoMother, Animal TempoFather){
        mother = TempoMother;
        father = TempoFather;
    }

    @Override
    public void run() {
        try {
            System.out.println(mother.getCanHaveBabies() + " " + father.getCanHaveBabies());
            Thread.sleep(5000);
            mother.setCanHaveBabies();
            father.setCanHaveBabies();
            System.out.println(mother.getCanHaveBabies() + " " + father.getCanHaveBabies());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package Model.Class;

import Model.Class.Zoo.Zoo;

import java.io.Serializable;

public class Player implements Serializable {
    private Zoo clientKaZoo = new Zoo();
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public Zoo getClientKaZoo() {
        return clientKaZoo;
    }

    public void setClientKaZoo(Zoo clientKaZoo) {
        this.clientKaZoo = clientKaZoo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

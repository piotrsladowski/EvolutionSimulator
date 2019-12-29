package evolutionSimulator.Models.Species;

import evolutionSimulator.Models.Species.Animals.Animal;

public class Herbivore implements Species {
    private int ID;
    private int speed;
    private String name;
    private int vitality;
    public Herbivore(int ID, String name, int speed, int vitality) {
        this.ID = ID;
        this.speed = speed;
        this.name = name;
        this.vitality = vitality;
    }
    @Override
    public void move() {

    }

    @Override
    public void copulate() {

    }

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getVitality() {
        return vitality;
    }

    @Override
    public void updateVitality() {
        this.vitality -= 10;
    }
}

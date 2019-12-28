package evolutionSimulator.Models.Species.Animals;

public class Herbivore implements Animal {
    private int ID;
    private int speed;
    private String name;
    public Herbivore(String name, int speed, int ID){
        this.ID = ID;
        this.speed = speed;
        this.name = name;
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
}

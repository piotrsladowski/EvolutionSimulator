package evolutionSimulator.Models.Species;

public class Plant implements Species{
    private int ID;
    private String name;

    public Plant(int ID, String name) {
        this.ID = ID;
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

    public String getName() {
        return this.name;
    }

    @Override
    public int getSpeed() {
        return 0;
    }

    @Override
    public int getVitality() {
        return 100;
    }
}

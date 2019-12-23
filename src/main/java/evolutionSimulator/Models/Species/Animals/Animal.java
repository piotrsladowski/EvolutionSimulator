package evolutionSimulator.Models.Species.Animals;

public interface Animal {
    public void move();
    public void copulate();
    public int getID();
    public String getName();
    public int getSpeed();
}

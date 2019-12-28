package evolutionSimulator.Models.Species.Animals;

public interface Animal {
    void move();
    void copulate();
    int getID();
    String getName();
    int getSpeed();
}

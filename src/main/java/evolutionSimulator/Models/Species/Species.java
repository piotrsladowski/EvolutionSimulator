package evolutionSimulator.Models.Species;

public interface Species {
    void move();
    void copulate();
    int getID();
    String getName();
    int getSpeed();
    int getVitality();
}

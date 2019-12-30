package evolutionSimulator.Models.Species;

import evolutionSimulator.Models.SingleCell;

public interface Species {
    void move(SingleCell[][] map, int x, int y, int size);
    void copulate();
    int getID();
    String getName();
    int getSpeed();
    int getVitality();
    void setVitality(int vitality);
    int updateVitality(SingleCell[][] map, int x, int y);
}

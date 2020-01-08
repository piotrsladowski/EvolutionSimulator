package evolutionSimulator.Models.Species;

import evolutionSimulator.Models.SingleCell;

import java.util.List;

public interface Species {

    boolean isAte();
    void setAte(boolean ate);
    void move(SingleCell[][] map, int x, int y, int size);
    void copulate(List<Species> speciesList);
    void eat(List<Species> speciesList);
    int getID();
    String getName();
    boolean isPregnant();
    void setPregnant(boolean pregnant);
    int getSpeed(); //this method is used in tableView
    int getVitality();
    void setVitality(int vitality);
    int updateVitality(SingleCell[][] map, int x, int y);
}

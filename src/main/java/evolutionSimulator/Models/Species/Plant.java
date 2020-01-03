package evolutionSimulator.Models.Species;

import evolutionSimulator.Models.SingleCell;

import javax.swing.*;
import java.util.List;

public class Plant implements Species{
    private int ID;
    private String name;
    private int vitality = 100;
    private boolean ate = false;
    private boolean pregned;
    public Plant(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }
    @Override
    public boolean isPregned() {
        return pregned;
    }
    @Override
    public void setPregned(boolean pregned) {
        this.pregned = pregned;
    }
    @Override
    public boolean isAte() {
        return ate;
    }

    @Override
    public void setAte(boolean ate) {
        this.ate = ate;
    }

    @Override
    public void move(SingleCell[][] map, int x, int y, int size) {

    }

    @Override
    public void copulate(List<Species> speciesList) {

    }

    @Override
    public void eat(List<Species> speciesList){}

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
        return  this.vitality;
    }

    @Override
    public void setVitality(int vitality){
        this.vitality = vitality;
    }

    @Override
    public int updateVitality(SingleCell[][] map, int x, int y) {
        if (this.vitality <= 0 || this.ate == true) {
            map[x][y].delete(this);
            return 0;
        }
        else {return 1;}

    }
}

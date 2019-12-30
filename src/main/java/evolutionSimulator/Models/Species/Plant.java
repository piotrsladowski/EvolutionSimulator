package evolutionSimulator.Models.Species;

import evolutionSimulator.Models.SingleCell;

import javax.swing.*;

public class Plant implements Species{
    private int ID;
    private String name;
    private int vitality = 100;
    public Plant(int ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    @Override
    public void move(SingleCell[][] map, int x, int y, int size) {

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
        return  this.vitality;
    }

    @Override
    public void setVitality(int vitality){
        this.vitality = vitality;
    };

    @Override
    public int updateVitality(SingleCell[][] map, int x, int y) {
        if (this.vitality <= 0) {
            map[x][y].delete(this);
            return 0;
        }
        else {return 1;}

    }
}

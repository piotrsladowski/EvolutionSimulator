package evolutionSimulator.Models.Species;

import evolutionSimulator.Models.SingleCell;

import java.util.List;

public class Plant implements Species{
    private final int ID;
    private final String name;
    private int vitality;
    private boolean ate = false;
    private boolean pregnant;
    public Plant(int ID, String name, int vitality) {
        this.ID = ID;
        this.name = name;
        this.vitality = vitality;
    }
    @Override
    public boolean isPregnant() {
        return pregnant;
    }
    @Override
    public void setPregnant(boolean pregnant) {
        this.pregnant = pregnant;
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
        if (this.vitality <= 0 || this.ate) {
            map[x][y].delete(this);
            return 0;
        }
        else {return 1;}

    }
}

package evolutionSimulator.Models.Species;

import evolutionSimulator.Models.SingleCell;

import java.util.List;
import java.util.Random;

public class Herbivore implements Species {
    private final int ID;
    private final int speed;
    private final String name;
    private int vitality;
    private final Random generator = new Random();
    private int intMove;
    private boolean ifMoved;
    private boolean ate = false;
    private boolean pregnant;
    public Herbivore(int ID, String name, int speed, int vitality, boolean ifMoved, boolean pregnant) {
        this.ID = ID;
        this.speed = speed;
        this.name = name;
        this.vitality = vitality;
        this.intMove = speed;
        this.ifMoved = ifMoved;
        this.pregnant = pregnant;
    }
    @Override
    public void move(SingleCell[][] map, int x, int y, int size) {
        if (this.ifMoved) {
            int deltaX = 0;
            int deltaY = 0;
            while (this.intMove != 0) {
                int direction = generator.nextInt(8);
                switch (direction) {
                    case 0:
                        deltaY = deltaY + 1;
                        break;
                    case 1:
                        deltaY = deltaY + 1;
                        deltaX = deltaX + 1;
                        break;
                    case 2:
                        deltaX = deltaX + 1;
                        break;
                    case 3:
                        deltaY = deltaY - 1;
                        deltaX = deltaX + 1;
                        break;
                    case 4:
                        deltaY = deltaY - 1;
                        break;
                    case 5:
                        deltaY = deltaY - 1;
                        deltaX = deltaX - 1;
                        break;
                    case 6:
                        deltaX = deltaX - 1;
                        break;
                    case 7:
                        deltaY = deltaY + 1;
                        deltaX = deltaX - 1;
                        break;
                }
                this.intMove = this.intMove - 1;
            }
            if (x + deltaX >= size || x + deltaX < 0) {
                deltaX = -deltaX;
            }
            if (y + deltaY >= size || y + deltaY < 0) {
                deltaY = -deltaY;
            }

            Herbivore copy_this = new Herbivore(this.ID, this.name, this.speed, this.vitality - 10, false, false);
            map[x + deltaX][y + deltaY].getAllSpecies().add(copy_this);
            this.vitality = 0;
        }
    }

    @Override
    public void copulate(List<Species> speciesList) {
        if (!this.pregnant) {
            for (Species species : speciesList) {
                if (species.getName().equals(this.name) && !species.isPregnant() && species != this) {
                    int newV = this.vitality/2;
                    int newVP = species.getVitality()/2;
                    Herbivore child = new Herbivore(this.ID,this.name,this.speed,newV+newVP,true,true);
                    this.vitality=newV;
                    species.setVitality(newVP);
                    speciesList.add(child);
                    this.pregnant =true;
                    species.setPregnant(true);
                    break;
                }
            }
        }

    }


    @Override
    public void eat(List<Species> speciesList){
        if (!this.ate) {
            if (this.vitality < 1000) {
            for (Species species : speciesList) {
                if (species.getClass().getName().equals("evolutionSimulator.Models.Species.Plant") && !species.isAte() && (species != this)){
                        this.vitality = this.vitality + species.getVitality();
                        species.setVitality(0);
                        species.setAte(true);
                        break;
                    }
                }
            }
            }
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
    public String getName() {
        return name;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getVitality() {
        return vitality;
    }

    @Override
    public void setVitality(int vitality){
        this.vitality = vitality;
    }

    @Override
    public int updateVitality(SingleCell[][] map, int x, int y) {
        if(this.vitality > 1000){
            this.vitality = 1000;
        }
        if (this.vitality <= 0 || this.ate) {
            map[x][y].delete(this);
            return 0;
        }
        else {
            this.ifMoved = true;
            this.pregnant = false;
            return 1;
        }
    }
}

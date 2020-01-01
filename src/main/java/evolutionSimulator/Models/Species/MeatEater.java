package evolutionSimulator.Models.Species;

import evolutionSimulator.Models.SingleCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MeatEater implements Species {
    private int ID;
    private int speed;
    private String name;
    private int vitality;
    private int intMove;
    private boolean been;
    private boolean ate = false;
    private boolean pregned;
    private Random generator = new Random();
    private ArrayList<String> eatLis = new ArrayList<String>();
    public MeatEater(int ID, String name, int speed, int vitality, boolean been, boolean pregned) {
        this.ID = ID;
        this.speed = speed;
        this.name = name;
        this.vitality = vitality;
        this.intMove = speed;
        this.been = been;
        this.pregned = pregned;
        this.eatLis.add("bear");
        this.eatLis.add("cow");
        this.eatLis.add("pig");
    }

    @Override
    public void move(SingleCell[][] map, int x, int y, int size) {
        if (this.been == true) {
            int deltaX = 0;
            int deltaY = 0;
            while (this.intMove > 0) {
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
            ;
            if (y + deltaY >= size || y + deltaY < 0) {
                deltaY = -deltaY;
            }
            ;
            MeatEater copy_this = new MeatEater(this.ID, this.name, this.speed, this.vitality - 10, false, false);
            map[x + deltaX][y + deltaY].getAllSpecies().add(copy_this);
            this.vitality = 0;
        }
    }

    @Override
    public void copulate(List<Species> speciesList) {
        if (this.pregned == false) {
            for (Species species : speciesList) {
                if (species.getName() == this.name && species.isPregned()==false && species != this) {
                    int newV = this.vitality/2;
                    int newVP = species.getVitality()/2;
                    MeatEater child = new MeatEater(this.ID,this.name,this.speed,newV+newVP,true,true);
                    this.vitality=newV;
                    species.setVitality(newVP);
                    speciesList.add(child);
                    this.pregned=true;
                    species.setPregned(true);
                    break;
                }
            }
        }

    }


    @Override
    public void eat(List<Species> speciesList){
        if (this.ate == false) {
        for (Species species : speciesList) {
            if (eatLis.contains(species.getName()) && species.isAte() == false && species != this) {
                this.vitality = this.vitality + species.getVitality();
                species.setVitality(0);
                species.setAte(true);
                break;

            }
        }
    }
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
    public int getID() {
        return ID;
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
    };

    @Override
    public int updateVitality(SingleCell[][] map, int x, int y) {
        if (this.vitality <= 0 || this.ate == true) {
            map[x][y].delete(this);
            return 0;
        }
        else {
            this.been = true;
            this.pregned = false;
            return 1;
        }
    }
}

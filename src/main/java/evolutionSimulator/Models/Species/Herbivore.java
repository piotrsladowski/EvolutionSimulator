package evolutionSimulator.Models.Species;

import evolutionSimulator.Models.SingleCell;

import java.util.Random;

public class Herbivore implements Species {
    private int ID;
    private int speed;
    private String name;
    private int vitality;
    private Random generator = new Random();
    private int intMove;
    private boolean been;
    public Herbivore(int ID, String name, int speed, int vitality, boolean been) {
        this.ID = ID;
        this.speed = speed;
        this.name = name;
        this.vitality = vitality;
        this.intMove = speed;
        this.been = been;
    }
    @Override
    public void move(SingleCell[][] map, int x, int y, int size) {
        if (this.been == true) {
            int deltaX = 0;
            int deltaY = 0;
            while (this.intMove != 0) {
                int direction = generator.nextInt(8);
                switch (direction) {
                    case 0:
                        deltaY = deltaY + 1;
                    case 1:
                        deltaY = deltaY + 1;
                        deltaX = deltaX + 1;
                    case 2:
                        deltaX = deltaX + 1;
                    case 3:
                        deltaY = deltaY - 1;
                        deltaX = deltaX + 1;
                    case 4:
                        deltaY = deltaY - 1;
                    case 5:
                        deltaY = deltaY - 1;
                        deltaX = deltaX - 1;
                    case 6:
                        deltaX = deltaX - 1;
                    case 7:
                        deltaY = deltaY + 1;
                        deltaX = deltaX - 1;
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
            Herbivore copy_this = new Herbivore(this.ID, this.name, this.speed, this.vitality - 10, false);
            map[x + deltaX][y + deltaY].getAllSpecies().add(copy_this);
            this.vitality = 0;
        }
    }

    @Override
    public Herbivore clone() throws CloneNotSupportedException {
        return (Herbivore) super.clone();
    }
    @Override
    public void copulate() {
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
        if (this.vitality <= 0) {
            map[x][y].delete(this);
            return 0;
        }
        else {this.been = true;}
            return 1;
    }
}

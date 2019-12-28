package evolutionSimulator.Models.Species.Animals;

import evolutionSimulator.Logic.Position;
import java.util.Random;

import static java.lang.StrictMath.round;

public class Lion {
    private String name = "Lion";
    private char symbol = 'L';
    int intMove = 4;
    int vitality;
    Random generator = new Random();
    Position position;

    public Lion(Position position) {
        this.position = position;
    }

    public void Go() {
        while (intMove > 0) {
            int direction = generator.nextInt(8);
            switch (direction) {
                case 0:
                    this.position.setY(this.position.getY() + 1);
                case 1:
                    this.position.setX(this.position.getX() + 1);
                    this.position.setY(this.position.getY() + 1);
                case 2:
                    this.position.setX(this.position.getX() + 1);
                case 3:
                    this.position.setX(this.position.getX() + 1);
                    this.position.setY(this.position.getY() - 1);
                case 4:
                    this.position.setY(this.position.getY() - 1);
                case 5:
                    this.position.setY(this.position.getY() - 1);
                    this.position.setX(this.position.getX() - 1);
                case 6:
                    this.position.setX(this.position.getX() - 1);
                case 7:
                    this.position.setY(this.position.getY() + 1);
                    this.position.setY(this.position.getY() - 1);
            }
            this.intMove = this.intMove - 1;
            this.vitality = this.vitality - 1;
        }
        intMove = 4;
    }

    public void EatCow(int cow_vitality){
        this.vitality = this.vitality + cow_vitality;
    }

    public void Breeding (){
        this.vitality = this.vitality - (int)round(this.vitality * 0.25);
        int x = this.position.getX()+1;
        int y = this.position.getY()+1;
        Position position = new Position(x,y);
        Lion lion = new Lion(position);
    }
}




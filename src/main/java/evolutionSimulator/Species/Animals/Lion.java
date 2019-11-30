package evolutionSimulator.Species.Animals;

import evolutionSimulator.Logic.Position;
import java.util.Random;

public class Lion {
    private String name = "Lion";
    private char symbol = 'L';
    int intMove = 4;
    Random generator = new Random();
    private Position position = new Position(generator.nextInt(10), generator.nextInt(10));

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
            intMove = intMove - 1;
        }
        intMove = 4;
    }

    public void breeding() {


    }
}




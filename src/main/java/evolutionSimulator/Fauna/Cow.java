package evolutionSimulator.Fauna;

import java.util.Random;
import evolutionSimulator.Logic.Position;
import evolutionSimulator.Logic.Mapa;

public class Cow {
    private String name = "Cow";
    private char symbol = 'C';
    Random generator = new Random();
    private Position position = new Position(generator.nextInt(10),generator.nextInt(10));
    //Dodaj do mapy obiekt
    int intMove = 2;

    public void Go(){
        while (intMove > 0){
            int direction  = generator.nextInt(8);
            switch(direction){
                case 0: this.position.setY(this.position.getY()+1);
                case 1: this.position.setX(this.position.getX()+1);
                        this.position.setY(this.position.getY()+1);
                case 2: this.position.setX(this.position.getX()+1);
                case 3: this.position.setX(this.position.getX()+1);
                        this.position.setY(this.position.getY()-1);
                case 4: this.position.setY(this.position.getY()-1);
                case 5: this.position.setY(this.position.getY()-1);
                        this.position.setX(this.position.getX()-1);
                case 6: this.position.setX(this.position.getX()-1);
                case 7: this.position.setY(this.position.getY()+1);
                        this.position.setY(this.position.getY()-1);
            }
            intMove = intMove -1;
        }
    intMove = 2;
    };



}


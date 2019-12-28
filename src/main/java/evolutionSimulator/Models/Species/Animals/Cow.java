package evolutionSimulator.Models.Species.Animals;

import java.util.Random;
import evolutionSimulator.Logic.Position;
import evolutionSimulator.Logic.Basic;

import static java.lang.StrictMath.round;

public class Cow {
    private String name = "Cow";
    private char symbol = 'C';
    Random generator = new Random();
    private Position position;
    //Dodaj do mapy obiekt
    int intMove = 2;
    int vitality = 100;

    public Cow(Position position) {
        this.position = position;
    }

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
            this.intMove = this.intMove -1;
            this.vitality = this.vitality -1;

        }
    intMove = 2;
    };

    public void EatGrass(int grass_vitality){
        this.vitality = this.vitality + grass_vitality;
    }
    public void EatLion(){
        this.vitality = 0;
    }
    public void Breeding (){
        this.vitality = this.vitality - (int)round(this.vitality * 0.25);
        int x = this.position.getX()+1;
        int y = this.position.getY()+1;
        Position position = new Position(x,y);
        Cow cow = new Cow(position);
    }

}


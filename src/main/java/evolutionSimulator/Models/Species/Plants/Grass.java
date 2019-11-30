package evolutionSimulator.Models.Species.Plants;

import evolutionSimulator.Logic.Position;
import java.util.Random;

public class Grass {
    private String name = "Grass";
    private char symbol = 'G';
    Random generator = new Random();
    private Position position = new Position(generator.nextInt(10), generator.nextInt(10));

}

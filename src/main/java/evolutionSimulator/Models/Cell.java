package evolutionSimulator.Models;

import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {
    //public int biomeID;
    public int[] animalIDArray;
    public int plantID;

    public Cell() {
        this.animalIDArray = new int[]{0,0};
    }
}

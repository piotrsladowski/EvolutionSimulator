package evolutionSimulator.Models;

import javafx.scene.shape.Rectangle;

public class CellGUI extends Rectangle {
    //public int biomeID;
    public int[] animalIDArray;
    public int plantID;

    public CellGUI() {
        this.animalIDArray = new int[]{0,0};
    }
}

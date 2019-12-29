package evolutionSimulator.Controllers;

import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Models.Species.Species;
import evolutionSimulator.View.MainWindow;
import javafx.scene.paint.ImagePattern;

import java.util.Map;

public class GUIUpdater {
    Map<String, ImagePattern> iconsList;
    private int gridSize;
    //private SingleCell[][] map;

    public GUIUpdater(Map<String, ImagePattern> iconsList, int gridSize) {
        this.iconsList = iconsList;
        this.gridSize = gridSize;
    }

    public void update(SingleCell[][] map){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if(map[i][j].hasAnySpecies()) {
                    Species item = map[i][j].getSpecies();
                    MainWindow.cellGUIArray[i][j].setFill(iconsList.get(item.getName()));
                    MainWindow.cellGUIArray[i][j].setOpacity(item.getVitality() * 0.01);
                }

            }
        }
    }
}

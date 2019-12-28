package evolutionSimulator.Controllers;

import evolutionSimulator.View.MainWindow;
import evolutionSimulator.Models.Cell;
import javafx.scene.paint.Color;

public class UIUpdater {
    private Cell[][] cells = MainWindow.cells;
    public void update(Cell[][] updatedCells){
        int size = updatedCells.length;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if (cells[i][j].animalIDArray != updatedCells[i][j].animalIDArray){
                    cells[i][j].animalIDArray = updatedCells[i][j].animalIDArray;
                    cells[i][j].setFill(Color.RED);
                }
                if (cells[i][j].plantID != updatedCells[i][j].plantID){
                    cells[i][j].plantID = updatedCells[i][j].plantID;
                }
            }
        }
    }
}

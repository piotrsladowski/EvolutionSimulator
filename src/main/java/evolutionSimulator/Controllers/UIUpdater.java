package evolutionSimulator.Controllers;

import evolutionSimulator.View.MainWindow;
import evolutionSimulator.Models.CellGUI;
import javafx.scene.paint.Color;

public class UIUpdater {
    private CellGUI[][] cellGUIS = MainWindow.cellGUIArray;
    public void update(CellGUI[][] updatedCellGUIS){
        int size = updatedCellGUIS.length;
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                if (cellGUIS[i][j].animalIDArray != updatedCellGUIS[i][j].animalIDArray){
                    cellGUIS[i][j].animalIDArray = updatedCellGUIS[i][j].animalIDArray;
                    cellGUIS[i][j].setFill(Color.RED);
                }
                if (cellGUIS[i][j].plantID != updatedCellGUIS[i][j].plantID){
                    cellGUIS[i][j].plantID = updatedCellGUIS[i][j].plantID;
                }
            }
        }
    }
}

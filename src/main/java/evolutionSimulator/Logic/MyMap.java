package evolutionSimulator.Logic;
import evolutionSimulator.View.MainWindow;
import evolutionSimulator.Models.Cell;
import evolutionSimulator.Models.SingleCell;

public class MyMap {
    private int gridSize;
    private SingleCell[][] map;

    public MyMap(int gridSize) {
        this.gridSize = gridSize;
        map = new SingleCell[gridSize][gridSize];
    }

    public SingleCell[][] build(){
        System.out.println(gridSize);
        Cell[][] cells = MainWindow.cells;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                SingleCell sc = new SingleCell();
                map[i][j] = sc;
                map[i][j].animalIDArray = new int[]{1,3};
                map[i][j].plantID = cells[i][j].plantID;
            }
        }
        return map;
    }
}

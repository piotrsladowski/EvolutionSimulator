package evolutionSimulator.View;

import evolutionSimulator.Logic.Map;
import evolutionSimulator.Models.Cell;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Controllers.ZoomableScrollPane;
import evolutionSimulator.Logic.Basic;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainWindow {
    private Stage stage;
    private static int gridSize;
    public static Cell[][] cells;
    public static StackPane[][] stackPanes;
    private GridPane mainGrid = new GridPane();
    private List<int[]> freeCells = new ArrayList<int[]>();

    public MainWindow(Stage stage, int gridSize){
        this.stage = stage;
        MainWindow.gridSize = gridSize;
        createListOFFreeCells();
        createCells();
        createStackPanes();
    }
    private static void createCells(){
        MainWindow.cells = new Cell[gridSize][gridSize];
    }

    private static void createStackPanes(){
        MainWindow.stackPanes = new StackPane[gridSize][gridSize];
    }

    private void createListOFFreeCells(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                freeCells.add(new int[]{i,j});
            }
        }
    }

    // Podsiadeł napisał, ale raczej już nie będzie użyte
    public static Cell[][] getCells() {
        if (MainWindow.cells == null)
            MainWindow.cells = new Cell[gridSize][gridSize];
        return MainWindow.cells;
    }


    private void addStackPanestoGrid(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                StackPane sP = new StackPane();
                sP.setStyle("-fx-background-color: brown; -fx-border-width: 1; -fx-border-color: black");
                MainWindow.stackPanes[i][j] = sP;
                mainGrid.add(sP,i,j);
            }
        }
    }

    public void build(int numOfLions, int numOfGrass) throws InterruptedException {
        Random rand = new Random();
        CustomIcons customIcons = new CustomIcons();
        addStackPanestoGrid();

        int size = freeCells.size();
        for (int i = 0; i < numOfGrass; i++) {
            int index = rand.nextInt(size-i);
            int[] cords = freeCells.get(index);
            freeCells.remove(index);
            Cell cell = new Cell();
            cell.setWidth(25);
            cell.setHeight(25);
            cell.setFill(customIcons.getIconGrass());
            MainWindow.cells[cords[0]][cords[1]] = cell;
            //cell.setFill(Color.TRANSPARENT);
            MainWindow.stackPanes[cords[0]][cords[1]].getChildren().add(cell);
        }
        size = freeCells.size();
        for (int i = 0; i < numOfLions; i++) {
            int index = rand.nextInt(size-i);
            int[] cords = freeCells.get(index);
            freeCells.remove(index);
            Cell cell = new Cell();
            cell.setWidth(25);
            cell.setHeight(25);
            cell.setFill(customIcons.getIconLion());
            MainWindow.cells[cords[0]][cords[1]] = cell;
            //cell.setStyle("-fx-border-style: solid; -fx-border-width: 5; -fx-border-color: red; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
            MainWindow.stackPanes[cords[0]][cords[1]].getChildren().add(cell);
        }

        size = freeCells.size();
        for (int i = 0; i < size; i++) {
            int index = rand.nextInt(size-i);
            int[] cords = freeCells.get(index);
            freeCells.remove(index);
            Cell cell = new Cell();
            cell.setWidth(25);
            cell.setHeight(25);
            cell.setFill(Color.GREEN);
            MainWindow.cells[cords[0]][cords[1]] = cell;
            MainWindow.stackPanes[cords[0]][cords[1]].getChildren().add(cell);
        }

        //mainGrid.setStyle("-fx-background-color: black; -fx-vgap: 1; -fx-hgap: 1");
        mainGrid.setStyle("-fx-background-color: black");
        ZoomableScrollPane mapRoot = new ZoomableScrollPane(mainGrid);
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MyMenuBar(stage).build();
        root.setTop(menuBar);
        root.setCenter(mapRoot);

        Map map = new Map(gridSize);
        SingleCell[][] maps = map.build();

        Scene scene = new Scene(root,500,400);
        stage.setScene(scene);
        stage.setTitle("Evolution Simulator");
        stage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Basic fef = new Basic(maps);
            }
        });
        stage.show();
    }
}

package evolutionSimulator.Components;

import evolutionSimulator.Models.Cell;
import evolutionSimulator.Models.CustomIcons;
import evolutionSimulator.Models.ZoomableScrollPane;
import evolutionSimulator.Logic.Basic;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
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
    GridPane mainGrid = new GridPane();
    List<int[]> freeCells = new ArrayList<int[]>();

    public MainWindow(Stage stage, int gridSize){
        this.stage = stage;
        MainWindow.gridSize = gridSize;
        createListOFFreeCells();
        createCells();
        createStackPanes();
    }
    public static void createCells(){
        MainWindow.cells = new Cell[gridSize][gridSize];
    }

    public static void createStackPanes(){
        MainWindow.stackPanes = new StackPane[gridSize][gridSize];
    }

    public void createListOFFreeCells(){
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
                sP.setStyle("-fx-background-color: brown");
                MainWindow.stackPanes[i][j] = sP;
                mainGrid.add(sP,i,j);
            }
        }
    }

    public void build(int numOfLions, int numOfGrass) throws InterruptedException {
        Random rand = new Random();
        CustomIcons customIcons = new CustomIcons();
        addStackPanestoGrid();
        int totalNum = numOfLions + numOfGrass;
/*        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Cell cell = new Cell();
                cell.setWidth(8);
                cell.setHeight(8);
                cell.setFill(customIcons.getIconGrass());
                MainWindow.stackPanes[i][j].getChildren().add(cell);
            }
        }*/

        for (int i = 0; i < numOfGrass; i++) {
            int index = rand.nextInt(freeCells.size());
            int[] coords = freeCells.get(index);
            freeCells.remove(index);
            Cell cell = new Cell();
            cell.setWidth(8);
            cell.setHeight(8);
            cell.setFill(customIcons.getIconGrass());
            //cell.setFill(Color.TRANSPARENT);
            MainWindow.stackPanes[coords[0]][coords[1]].getChildren().add(cell);
        }
        for (int i = 0; i < numOfLions; i++) {
            int index = rand.nextInt(freeCells.size());
            int[] coords = freeCells.get(index);
            freeCells.remove(index);
            Cell cell = new Cell();
            cell.setWidth(8);
            cell.setHeight(8);
            cell.setFill(customIcons.getIconLion());
            //cell.setStyle("-fx-border-style: solid; -fx-border-width: 5; -fx-border-color: red; -fx-min-width: 20; -fx-min-height:20; -fx-max-width:20; -fx-max-height: 20;");
            MainWindow.stackPanes[coords[0]][coords[1]].getChildren().add(cell);
        }


        //mainGrid.setStyle("-fx-background-color: black; -fx-vgap: 1; -fx-hgap: 1");
        mainGrid.setStyle("-fx-background-color: black");
        //mainGrid.setStyle("-fx-background-color: red");
        ZoomableScrollPane mapRoot = new ZoomableScrollPane(mainGrid);
        MenuBar menuBar = new MenuBar();
        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Info");
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(mapRoot);

        Scene scene = new Scene(root,500,400);
        //stage.setScene(new Scene(root,500,400));
        stage.setScene(scene);
        stage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Basic fef = new Basic();
            }
        });
        stage.show();
    }
}

package evolutionSimulator.View;

import evolutionSimulator.Models.Logic.MyMap;
import evolutionSimulator.Models.CellGUI;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Controllers.ZoomableScrollPane;
import evolutionSimulator.Models.Logic.Basic;
import evolutionSimulator.Models.Species.Animals.Animal;
import evolutionSimulator.Models.Species.Plant;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.*;

public class MainWindow {
    private Stage stage;
    private static int gridSize;
    public static CellGUI[][] cellGUIS;
    public static StackPane[][] stackPanes;
    private GridPane mainGrid = new GridPane();
    private List<int[]> freeCells = new ArrayList<int[]>();

    private SingleCell[][] map;

    private Map<String, ImagePattern> iconsList = new HashMap<String, ImagePattern>();

    public MainWindow(Stage stage, int gridSize, SingleCell[][] map){
        this.stage = stage;
        MainWindow.gridSize = gridSize;
        this.map = map;
        createListOFFreeCells();
        createCells();
        createStackPanes();
    }
    private static void createCells(){
        MainWindow.cellGUIS = new CellGUI[gridSize][gridSize];
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

    private void addStackPanesToGrid(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                StackPane sP = new StackPane();
                sP.setStyle("-fx-background-color: brown; -fx-border-width: 1; -fx-border-color: black");
                MainWindow.stackPanes[i][j] = sP;
                mainGrid.add(sP,i,j);
            }
        }
    }

    public void generateIcons(List<String[]> readAnimals, List<String[]> readPlants){
        for (String[] animal: readAnimals) {
            CustomIcons customIcons = new CustomIcons();
            ImagePattern icon = customIcons.generateImagePattern(animal[2]);
            iconsList.put(animal[2], icon);
        }
    }

    public void build(List<String[]> readAnimals, List<String[]> readPlants) {
        //Random rand = new Random();
        //CustomIcons customIcons = new CustomIcons();
        addStackPanesToGrid();

        for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if(map[i][j].isPlant()){
                        Plant plant = map[i][j].getPlant();
                        CellGUI cellGUI = new CellGUI();
                        cellGUI.setWidth(25);
                        cellGUI.setHeight(25);
                        cellGUI.setFill(iconsList.get(plant.getName()));
                        MainWindow.stackPanes[i][j].getChildren().add(cellGUI);
                    }
                    else if(map[i][j].isAnimal()) {
                        Animal animal = map[i][j].getAnimal();
                        CellGUI cellGUI = new CellGUI();
                        cellGUI.setWidth(25);
                        cellGUI.setHeight(25);
                        cellGUI.setFill(iconsList.get(animal.getName()));
                        MainWindow.stackPanes[i][j].getChildren().add(cellGUI);
                    }
                    else {
                        CellGUI cellGUI = new CellGUI();
                        cellGUI.setWidth(25);
                        cellGUI.setHeight(25);
                        cellGUI.setFill(Color.GREEN);
                    }
            }
        }

/*
        int size;

        // Insert randomly animals on the map
        for (String[] animal: readAnimals) {
            size = freeCells.size();
            ImagePattern icon = customIcons.generateImagePattern(animal[2]);
            for (int i = 0; i < Integer.parseInt(animal[1]); i++) {
                int index = rand.nextInt(size-i);
                int[] cords = freeCells.get(index);
                freeCells.remove(index);
                CellGUI cellGUI = new CellGUI();
                cellGUI.setWidth(25);
                cellGUI.setHeight(25);
                cellGUI.setFill(icon);
                cellGUI.animalIDArray[0] = Integer.parseInt(animal[0]);
                MainWindow.cellGUIS[cords[0]][cords[1]] = cellGUI;
                MainWindow.stackPanes[cords[0]][cords[1]].getChildren().add(cellGUI);
            }
        }

        // Insert randomly plants on the map
        for (String[] plant: readPlants) {
            size = freeCells.size();
            ImagePattern icon = customIcons.generateImagePattern(plant[2]);
            for (int i = 0; i < Integer.parseInt(plant[1]); i++) {
                int index = rand.nextInt(size-i);
                int[] cords = freeCells.get(index);
                freeCells.remove(index);
                CellGUI cellGUI = new CellGUI();
                cellGUI.setWidth(25);
                cellGUI.setHeight(25);
                cellGUI.setFill(icon);
                //cell.setOpacity(0.4);
                cellGUI.plantID = Integer.parseInt(plant[0]);
                MainWindow.cellGUIS[cords[0]][cords[1]] = cellGUI;
                MainWindow.stackPanes[cords[0]][cords[1]].getChildren().add(cellGUI);
            }
        }

        //generate cells not occupied by any species
        size = freeCells.size();
        for (int i = 0; i < size; i++) {
            int index = rand.nextInt(size-i);
            int[] cords = freeCells.get(index);
            freeCells.remove(index);
            CellGUI cellGUI = new CellGUI();
            cellGUI.setWidth(25);
            cellGUI.setHeight(25);
            cellGUI.setFill(Color.GREEN);
            MainWindow.cellGUIS[cords[0]][cords[1]] = cellGUI;
            MainWindow.stackPanes[cords[0]][cords[1]].getChildren().add(cellGUI);
        }*/

        //mainGrid.setStyle("-fx-background-color: black; -fx-vgap: 1; -fx-hgap: 1");
        mainGrid.setStyle("-fx-background-color: black");
        ZoomableScrollPane mapRoot = new ZoomableScrollPane(mainGrid);
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MyMenuBar(stage).build();
        root.setTop(menuBar);
        root.setCenter(mapRoot);

        //MyMap myMap = new MyMap(gridSize);
        //SingleCell[][] maps = myMap.build();

        Scene scene = new Scene(root,500,400);
        stage.setScene(scene);
        stage.setTitle("Evolution Simulator");
        stage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //Basic fef = new Basic(maps);
            }
        });
        stage.show();
    }
}

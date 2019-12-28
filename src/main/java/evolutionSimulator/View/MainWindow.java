package evolutionSimulator.View;

import evolutionSimulator.Models.CellGUI;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Controllers.ZoomableScrollPane;
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
    public static CellGUI[][] cellGUIArray;
    private StackPane[][] stackPanes;
    private GridPane mainGrid = new GridPane();

    private SingleCell[][] map;

    private Map<String, ImagePattern> iconsList = new HashMap<>();

    public MainWindow(Stage stage, int gridSize, SingleCell[][] map){
        this.stage = stage;
        MainWindow.gridSize = gridSize;
        this.map = map;
        createCellsGUI();
        createStackPanes();
    }
    private static void createCellsGUI(){
        MainWindow.cellGUIArray = new CellGUI[gridSize][gridSize];
    }

    private void createStackPanes(){
        stackPanes = new StackPane[gridSize][gridSize];
    }

    private void addStackPanesToGrid(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                StackPane sP = new StackPane();
                sP.setStyle("-fx-background-color: brown; -fx-border-width: 1; -fx-border-color: black");
                stackPanes[i][j] = sP;
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
        for (String[] plant: readPlants) {
            CustomIcons customIcons = new CustomIcons();
            ImagePattern icon = customIcons.generateImagePattern(plant[2]);
            iconsList.put(plant[2], icon);
        }
    }

    public void build() {
        addStackPanesToGrid();

        for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if(map[i][j].isPlant()){
                        Plant plant = map[i][j].getPlant();
                        CellGUI cellGUI = new CellGUI();
                        cellGUI.setWidth(25);
                        cellGUI.setHeight(25);
                        cellGUI.setFill(iconsList.get(plant.getName()));
                        cellGUIArray[i][j] = cellGUI;
                        stackPanes[i][j].getChildren().add(cellGUI);
                    }
                    else if(map[i][j].isAnimal()) {
                        Animal animal = map[i][j].getAnimal();
                        CellGUI cellGUI = new CellGUI();
                        cellGUI.setWidth(25);
                        cellGUI.setHeight(25);
                        cellGUI.setFill(iconsList.get(animal.getName()));
                        cellGUIArray[i][j] = cellGUI;
                        stackPanes[i][j].getChildren().add(cellGUI);
                    }
                    else {
                        CellGUI cellGUI = new CellGUI();
                        cellGUI.setWidth(25);
                        cellGUI.setHeight(25);
                        cellGUI.setFill(Color.GREEN);
                        cellGUIArray[i][j] = cellGUI;
                        stackPanes[i][j].getChildren().add(cellGUI);
                    }
            }
        }

        //mainGrid.setStyle("-fx-background-color: black; -fx-vgap: 1; -fx-hgap: 1");
        mainGrid.setStyle("-fx-background-color: black");
        ZoomableScrollPane mapRoot = new ZoomableScrollPane(mainGrid);
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MyMenuBar(stage).build();
        root.setTop(menuBar);
        root.setCenter(mapRoot);

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

    public void refresh(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                cellGUIArray[i][j].setFill(iconsList.get(map[i][j].getName()));
            }
        }
    }
}

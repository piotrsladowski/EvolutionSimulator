package evolutionSimulator.View;

import evolutionSimulator.Models.CellGUI;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Controllers.ZoomableScrollPane;
import evolutionSimulator.Models.Species.Animals.Animal;
import evolutionSimulator.Models.Species.Plant;
import evolutionSimulator.Models.Species.Species;
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
    private Object pauseLock;

    private Properties properties;

    public MainWindow(Stage stage, int gridSize, SingleCell[][] map, Properties properties, Object pauseLock){
        this.stage = stage;
        MainWindow.gridSize = gridSize;
        this.map = map;
        this.properties = properties;
        this.pauseLock = pauseLock;
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

    public Map<String, ImagePattern> generateIcons(List<String[]> readAnimals, List<String[]> readPlants){
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
        return iconsList;
    }

    public void build() {
        addStackPanesToGrid();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if(map[i][j].hasAnySpecies()){
                    Species species = map[i][j].getSpecies();
                    CellGUI cellGUI = new CellGUI();
                    cellGUI.setWidth(25);
                    cellGUI.setHeight(25);
                    cellGUI.setFill(iconsList.get(species.getName()));
                    cellGUI.setOpacity(species.getVitality() * 0.01);
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
        MenuBar menuBar = new MyMenuBar(stage, properties, pauseLock).build();
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
                Species item = map[i][j].getSpecies();
                MainWindow.cellGUIArray[i][j].setFill(iconsList.get(item.getName()));
                MainWindow.cellGUIArray[i][j].setOpacity(item.getVitality() * 0.01);
            }
        }
    }
}

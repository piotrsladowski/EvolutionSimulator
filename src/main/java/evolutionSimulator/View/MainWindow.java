package evolutionSimulator.View;

import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Controllers.ZoomableScrollPane;
import evolutionSimulator.Models.Species.Species;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.*;

public class MainWindow {
    private final Stage stage;
    private static int gridSize;
    public static CellGUI[][] cellGUIArray;
    private StackPane[][] stackPanes;
    private final GridPane mainGrid = new GridPane();
    private final SingleCell[][] map;
    private final Map<String, ImagePattern> iconsList = new HashMap<>();
    private final Object pauseLock;

    private final Properties properties;

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
                CellGUI cellGUI = new CellGUI();
                cellGUI.addAllSpecies(map[i][j].getAllSpecies());
                if(map[i][j].hasAnySpecies()){
                    Species species = map[i][j].getSpecies();
                    cellGUI.setWidth(25);
                    cellGUI.setHeight(25);
                    cellGUI.setFill(iconsList.get(species.getName()));
                    cellGUI.setOpacity(species.getVitality() * 0.01);
                }
                else {
                    cellGUI.setWidth(25);
                    cellGUI.setHeight(25);
                    cellGUI.setFill(Color.GREEN);
                }
                cellGUIArray[i][j] = cellGUI;
                stackPanes[i][j].getChildren().add(cellGUI);
            }
        }

        // add e
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int finalI = i;
                int finalJ = j;
                stackPanes[i][j].setOnMouseClicked(mouseEvent -> {
                    if(properties.getProperty("paused").equals("true")) {
                        final Stage dialog = new Stage();
                        dialog.initModality(Modality.APPLICATION_MODAL);
                        dialog.initOwner(stage);
                        TableView<Species> table = new TableView<>();
                        TableColumn<Species, String> column1 = new TableColumn<>("Name");
                        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
                        TableColumn<Species, String> column2 = new TableColumn<>("Vitality");
                        column2.setCellValueFactory(new PropertyValueFactory<>("vitality"));
                        TableColumn<Species, String> column3 = new TableColumn<>("Speed");
                        column3.setCellValueFactory(new PropertyValueFactory<>("speed"));
                        table.getColumns().add(column1);
                        table.getColumns().add(column2);
                        table.getColumns().add(column3);
                        for (Species species: MainWindow.cellGUIArray[finalI][finalJ].getSpeciesList()) {
                            table.getItems().add(species);
                        }

                        dialog.setTitle(String.format("Cell info col:%d  row:%d", finalI+1, finalJ+1));
                        Scene dialogScene = new Scene(table, 300, 200);
                        dialog.setScene(dialogScene);
                        dialog.show();
                    }
                });
            }
        }

        mainGrid.setStyle("-fx-background-color: black");
        ZoomableScrollPane mapRoot = new ZoomableScrollPane(mainGrid);
        BorderPane root = new BorderPane();
        MenuBar menuBar = new MyMenuBar(stage, properties, pauseLock).build();
        root.setTop(menuBar);
        root.setCenter(mapRoot);

        Scene scene = new Scene(root,500,400);
        stage.setScene(scene);
        stage.setTitle("Evolution Simulator");
        stage.setOnShowing(event -> {
            //do sth
        });
        stage.show();
    }
}

package evolutionSimulator;

import evolutionSimulator.Controllers.ConfigFile;
import evolutionSimulator.Models.Logic.MyMap;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.View.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigFile configFile = new ConfigFile(getClass().getResource("/config.test").getFile());
        configFile.load();
        List<String[]> readAnimals = configFile.getValidAnimals();
        List<String[]> readPlants = configFile.getValidPlants();
        int gridSize = Integer.parseInt(configFile.getGeneralProperties().get("gridSize"));


        MyMap myMap = new MyMap(gridSize);
        SingleCell[][] map = myMap.generate(readAnimals, readPlants);
        MainWindow mainWindow = new MainWindow(primaryStage, gridSize, map);
        mainWindow.generateIcons(readAnimals, readPlants);
        mainWindow.build(readAnimals, readPlants);

    }

    public static void main(String[] args) {
        launch(args);
    }
}

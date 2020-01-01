package evolutionSimulator;

import evolutionSimulator.Controllers.ConfigFile;
import evolutionSimulator.Controllers.GUIUpdater;
import evolutionSimulator.Models.Logic.MapUpdater;
import evolutionSimulator.Models.Logic.MyMap;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.View.MainWindow;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // read config files
        ConfigFile configFile = new ConfigFile(getClass().getResource("/config.test").getFile());
        configFile.load();
        List<String[]> readAnimals = configFile.getValidAnimals();
        List<String[]> readPlants = configFile.getValidPlants();
        int gridSize = Integer.parseInt(configFile.getGeneralProperties().get("gridSize"));

        Properties properties = new Properties();
        final Object pauseLock = new Object();
        properties.setProperty("year", "0");
        properties.setProperty("day", "1");

        //TODO add to config file
        properties.setProperty("paused", "false");
        properties.setProperty("spawnPlants", "false");
        properties.setProperty("procreationEnabled", "true");
        properties.setProperty("eatingEnabled", "true");
        properties.setProperty("motionEnabled", "true");

        // generate maps (front and back)
        MyMap myMap = new MyMap(gridSize);
        SingleCell[][] map = myMap.generate(readAnimals, readPlants);
        MainWindow mainWindow = new MainWindow(primaryStage, gridSize, map, properties, pauseLock);
        Map<String, ImagePattern> iconsList = mainWindow.generateIcons(readAnimals, readPlants);
        mainWindow.build();

        // give control to the Logic function
        GUIUpdater guiUpdater = new GUIUpdater(iconsList, gridSize);
        MapUpdater mapUpdater = new MapUpdater(map, guiUpdater, properties, pauseLock);
        mapUpdater.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

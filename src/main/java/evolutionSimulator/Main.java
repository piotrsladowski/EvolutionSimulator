package evolutionSimulator;

import evolutionSimulator.Controllers.ConfigFile;
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
        MainWindow mainWindow = new MainWindow(primaryStage, gridSize);
        mainWindow.build(10, readAnimals, readPlants);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

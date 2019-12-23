package evolutionSimulator;

import evolutionSimulator.ConfigFile.ConfigFile;
import evolutionSimulator.Models.Species.Animals.Animal;
import evolutionSimulator.View.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigFile configFile = new ConfigFile(getClass().getResource("/config.test").getFile());
        configFile.load();
        ArrayList<Animal> correctlyReadAnimals = configFile.getCorrectlyReadAnimals();
        int gridSize = Integer.parseInt(configFile.getGeneralProperties().get("gridSize"));
        //int gridSize = 20;
        MainWindow mainWindow = new MainWindow(primaryStage, gridSize);
        mainWindow.build(10, 10, correctlyReadAnimals);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

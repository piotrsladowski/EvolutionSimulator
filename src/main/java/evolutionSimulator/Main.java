package evolutionSimulator;

import evolutionSimulator.ConfigFile.ConfigFile;
import evolutionSimulator.Controllers.LoadSettings;
import evolutionSimulator.View.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ConfigFile configFile = new ConfigFile(getClass().getResource("/config.test").getFile());
        configFile.load();
        int gridSize = Integer.parseInt(configFile.getGeneralProperties().get("gridSize"));
        MainWindow mainWindow = new MainWindow(primaryStage, gridSize);
        mainWindow.build(10, 10);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

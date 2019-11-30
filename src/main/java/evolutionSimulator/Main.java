package evolutionSimulator;

import evolutionSimulator.Controllers.LoadSettings;
import evolutionSimulator.View.MainWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoadSettings loadSettings = new LoadSettings();
        loadSettings.loadGeneral();
        MainWindow mainWindow = new MainWindow(primaryStage, 50);
        mainWindow.build(10, 10);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

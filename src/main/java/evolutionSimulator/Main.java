package evolutionSimulator;

import evolutionSimulator.Components.MainWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
/*        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Czesc");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/


        MainWindow mainWindow = new MainWindow(primaryStage, 50);
        mainWindow.build(10,10);

    }


    public static void main(String[] args) {
        launch(args);
    }
}

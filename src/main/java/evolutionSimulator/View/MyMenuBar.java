package evolutionSimulator.View;

import evolutionSimulator.Controllers.ControlWindow;
import evolutionSimulator.Controllers.MyLogger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyMenuBar {
    private final Stage parentStage;
    private Stage aboutStage = null;
    private Stage controlStage = null;
    private final Properties properties;
    private final Object pauseLock;

    public MyMenuBar(Stage parentStage, Properties properties, Object pauseLock) {
        this.parentStage = parentStage;
        this.properties = properties;
        this.pauseLock = pauseLock;
    }

    public javafx.scene.control.MenuBar build(){
        MenuItem control = new MenuItem("Open control window");
        control.setOnAction(event -> {
            try {
                if(controlStage == null){
                    controlStage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/Control.fxml"));
                    Parent root = fxmlLoader.load();
                    fxmlLoader.<ControlWindow>getController().setProperties(properties);
                    fxmlLoader.<ControlWindow>getController().setPauseLock(pauseLock);
                    //fxmlLoader.setController(new ControlWindow(properties));
                    Scene controlScene = new Scene(root);
                    controlStage.setTitle("Control");
                    controlStage.setScene(controlScene);
                    controlStage.initOwner(parentStage);
                    controlStage.setOnCloseRequest(event1 -> controlStage = null);
                    controlStage.show();
                }
                else {
                    System.out.println("Control stage is not null");
                }
            }
            catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        });

        MenuItem about = new MenuItem("About");
        about.setOnAction(event -> {
            try{
                if(aboutStage == null) {
                    aboutStage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/fxml/About.fxml"));
                    Scene aboutScene = new Scene(fxmlLoader.load());
                    aboutStage.setTitle("About");
                    aboutStage.setScene(aboutScene);
                    aboutStage.initModality(Modality.NONE);
                    aboutStage.initOwner(parentStage);
                    aboutStage.setResizable(false);
                    aboutStage.show();
                }
                if(!aboutStage.isShowing()){
                    aboutStage.show();
                }
            }
            catch (IOException e){
                MyLogger.newLogSevere("Failed to create new About Window.");
            }
        });
        MenuItem githubPage = new MenuItem("Project website");
        githubPage.setOnAction(event -> {
            URI u = null;
            try {
                u = new URI("https://github.com/piotrsladowski/EvolutionSimulator");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            try {
                assert u != null;
                java.awt.Desktop.getDesktop().browse(u);
            } catch (IOException e) {
                e.printStackTrace();
                MyLogger.newLogSevere("Failed to open a Github website");
            }
        });
        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Info");
        menu1.getItems().addAll(control);
        menu2.getItems().addAll(about, githubPage);
        javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        MyLogger.newLogInfo("Successfully generated MenuBar");
        return menuBar;
    }
}

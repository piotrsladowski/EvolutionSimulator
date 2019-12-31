package evolutionSimulator.View;

import evolutionSimulator.Controllers.ControlWindow;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyMenuBar {
    private Stage parentStage;
    private Stage aboutStage = null;
    private Stage controlStage = null;
    private Properties properties;
    private Object pauseLock;

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
                    Parent root = (Parent)fxmlLoader.load();
                    fxmlLoader.<ControlWindow>getController().setProperties(properties);
                    fxmlLoader.<ControlWindow>getController().setPauseLock(pauseLock);
                    //fxmlLoader.setController(new ControlWindow(properties));
                    Scene controlScene = new Scene(root);
                    controlStage.setTitle("Control");
                    controlStage.setScene(controlScene);
                    controlStage.initOwner(parentStage);
                    controlStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            controlStage = null;
                        }
                    });
                    controlStage.show();
                }
                else {
                    System.out.println("Control stage is not null");
                }
/*                if(!controlStage.isShowing()){
                    controlStage.show();
                }*/
            }
            catch (IOException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        });

        MenuItem about = new MenuItem("About");
/*        EventHandler<ActionEvent> aboutClick = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            }
        };*/
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
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window.", e);
            }
        });
        MenuItem githubPage = new MenuItem("Github website");
        githubPage.setOnAction(event -> {
            URI u = null;
            try {
                u = new URI("https://github.com");
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            try {
                assert u != null;
                java.awt.Desktop.getDesktop().browse(u);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Info");
        menu1.getItems().addAll(control);
        menu2.getItems().addAll(about, githubPage);
        javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        return menuBar;
    }
}

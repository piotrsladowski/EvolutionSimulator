package evolutionSimulator.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyMenuBar {
    private Stage parentStage;
    private Stage aboutStage = null;

    public MyMenuBar(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public javafx.scene.control.MenuBar build(){
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
                    Scene aboutScene = new Scene(fxmlLoader.load(), 400, 300);
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
                java.awt.Desktop.getDesktop().browse(u);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        final Menu menu1 = new Menu("File");
        final Menu menu2 = new Menu("Info");
        menu2.getItems().addAll(about, githubPage);
        javafx.scene.control.MenuBar menuBar = new javafx.scene.control.MenuBar();
        menuBar.getMenus().add(menu1);
        menuBar.getMenus().add(menu2);
        return menuBar;
    }
}

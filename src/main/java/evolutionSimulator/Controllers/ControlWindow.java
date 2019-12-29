package evolutionSimulator.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

//import java.awt.*;

public class ControlWindow {

    @FXML
    private Label label1;

    @FXML
    public void initialize(){
        label1.setText("Dzień pierwszy");
    }
}

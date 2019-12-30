package evolutionSimulator.Controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.beans.EventHandler;
import java.util.Properties;

public class ControlWindow extends Thread{
    private Properties properties;
    private int yearNum;
    private int dayNum;
    private boolean paused;

    public void setProperties(Properties properties){
        this.properties = properties;
    }

    @FXML
    private Label yearNumber;

    @FXML
    private Label dayNumber;

    @FXML
    private Button pause_button;

    @FXML
    private Button resume_button;

    @FXML
    private Button nextDay_button;


    @FXML
    public void initialize(){
        new Thread(task).start();
        Platform.runLater((() -> {
            paused = Boolean.parseBoolean(properties.getProperty("paused"));
            task.valueProperty().addListener((dayNum, oldValue, newValue) -> {
                if(newValue != null) {
                    dayNumber.setText(String.valueOf(newValue));
                    yearNumber.setText(String.valueOf(yearNum));
                }

            });
            if(paused){
                pause_button.setDisable(true);
            }
            else {
                resume_button.setDisable(true);
                nextDay_button.setDisable(true);
            }
        }));


    }

    Task<Integer> task = new Task<Integer>() {
        @Override
        protected Integer call() throws Exception {
            for (int i = 0; i < 1000; i++) {
                dayNum = Integer.parseInt(properties.getProperty("day"));
                yearNum = Integer.parseInt(properties.getProperty("year"));
                updateValue(dayNum);
                Thread.sleep(500);
            }
            return dayNum;
        }
    };

    @FXML
    public void pauseButtonHandle(ActionEvent actionEvent) {
        paused = true;
        properties.setProperty("paused", "true");
        setButtons();
    }

    private void setButtons(){
        if(paused){
            pause_button.setDisable(true);
            resume_button.setDisable(false);
            nextDay_button.setDisable(false);
        }
        else{
            pause_button.setDisable(false);
            resume_button.setDisable(true);
            nextDay_button.setDisable(true);
        }
    }

    public void resumeButtonHandle(ActionEvent actionEvent) {
        paused = false;
        properties.setProperty("paused", "false");
        setButtons();
    }

    public void nextDayButtonHandle(ActionEvent actionEvent) {

    }
}


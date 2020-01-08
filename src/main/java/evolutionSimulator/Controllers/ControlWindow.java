package evolutionSimulator.Controllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Properties;
import java.util.regex.Pattern;

public class ControlWindow extends Thread{
    private Properties properties;
    private int yearNum;
    private int dayNum;
    private boolean paused;
    private boolean spawnPlants;
    private boolean procreationEnabled;
    private boolean eatingEnabled;
    private boolean motionEnabled;
    private Object pauseLock;

    public void setProperties(Properties properties){
        this.properties = properties;
    }
    public void setPauseLock(Object pauseLock){
        this.pauseLock = pauseLock;
    }

    //region Buttons Declaration

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
    private Button plantSpawnStart_button;

    @FXML
    private Button plantSpawnStop_button;

    @FXML
    private Button procreationStart_button;

    @FXML
    private Button procreationStop_button;

    @FXML
    private Button eatStart_button;

    @FXML
    private Button eatStop_button;

    @FXML
    private Button motionStart_button;

    @FXML
    private Button motionStop_button;

    @FXML
    private Button refreshTime_button;

    @FXML
    private Button removeAllPlants_button;

    @FXML
    private TextField refreshTime_textField;

    //endregion

    @FXML
    public void initialize(){
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        //new Thread(task).start();
        Platform.runLater((() -> {
            paused = Boolean.parseBoolean(properties.getProperty("paused"));
            spawnPlants = Boolean.parseBoolean(properties.getProperty("spawnPlants"));
            procreationEnabled = Boolean.parseBoolean(properties.getProperty("procreationEnabled"));
            eatingEnabled = Boolean.parseBoolean(properties.getProperty("eatingEnabled"));
            motionEnabled = Boolean.parseBoolean(properties.getProperty("motionEnabled"));
            refreshTime_textField.setText(properties.getProperty("refreshTime"));

            refreshTime_textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                //allow only 9 digits to prevent int overflow
                if(Pattern.matches("^[0-9]{0,9}+$", newValue)) {
                    refreshTime_button.setDisable(false);
                } else {
                    refreshTime_button.setDisable(true);
                }

            });

            task.valueProperty().addListener((dayNum, oldValue, newValue) -> {
                if(newValue != null) {
                    dayNumber.setText(String.valueOf(newValue));
                    yearNumber.setText(String.valueOf(yearNum));
                }

            });
            setButtons();
        }));


    }

    final Task<Integer> task = new Task<>() {
        @Override
        protected Integer call() throws Exception {
            for (int i = 0; i < 32000; i++) {

                dayNum = Integer.parseInt(properties.getProperty("day"));
                yearNum = Integer.parseInt(properties.getProperty("year"));
                updateValue(dayNum);
                Thread.sleep(500);
            }
            return dayNum;
        }
    };

    @FXML
    public void pauseButtonHandle() {
        paused = true;
        properties.setProperty("paused", "true");
        setButtons();
    }

    private void setButtons(){
        if(paused){
            pause_button.setDisable(true);
            resume_button.setDisable(false);
            nextDay_button.setDisable(false);
        } else{
            pause_button.setDisable(false);
            resume_button.setDisable(true);
            nextDay_button.setDisable(true);
        }

        if(spawnPlants){
            plantSpawnStop_button.setDisable(false);
            plantSpawnStart_button.setDisable(true);
        } else {
            plantSpawnStop_button.setDisable(true);
            plantSpawnStart_button.setDisable(false);
        }

        if(procreationEnabled){
            procreationStart_button.setDisable(true);
            procreationStop_button.setDisable(false);
        } else {
            procreationStart_button.setDisable(false);
            procreationStop_button.setDisable(true);
        }

        if(eatingEnabled){
            eatStart_button.setDisable(true);
            eatStop_button.setDisable(false);
        }
        else {
            eatStart_button.setDisable(false);
            eatStop_button.setDisable(true);
        }

        if(motionEnabled){
            motionStart_button.setDisable(true);
            motionStop_button.setDisable(false);
        } else {
            motionStart_button.setDisable(false);
            motionStop_button.setDisable(true);
        }
    }

    public void resumeButtonHandle() {
        synchronized (pauseLock){
            pauseLock.notify();
        }
        paused = false;
        properties.setProperty("paused", "false");
        setButtons();
    }

    public void nextDayButtonHandle() {
        synchronized (pauseLock){
            pauseLock.notify();
        }
        properties.setProperty("paused", "true");
    }

    public void plantSpawnStartButtonHandle() {
        spawnPlants = true;
        properties.setProperty("spawnPlants", "true");
        setButtons();
    }

    public void plantSpawnStopButtonHandle() {
        spawnPlants = false;
        properties.setProperty("spawnPlants", "false");
        setButtons();
    }

    public void procreationStartButtonHandle() {
        procreationEnabled = true;
        properties.setProperty("procreationEnabled", "true");
        setButtons();
    }

    public void procreationStopButtonHandle() {
        procreationEnabled = false;
        properties.setProperty("procreationEnabled", "false");
        setButtons();
    }

    public void eatStartButtonHandle() {
        eatingEnabled = true;
        properties.setProperty("eatingEnabled", "true");
        setButtons();
    }

    public void eatStopButtonHandle() {
        eatingEnabled = false;
        properties.setProperty("eatingEnabled", "false");
        setButtons();
    }

    public void motionStartButtonHandle() {
        motionEnabled = true;
        properties.setProperty("motionEnabled", "true");
        setButtons();
    }

    public void motionStopButtonHandle() {
        motionEnabled = false;
        properties.setProperty("motionEnabled", "false");
        setButtons();
    }

    public void removePlantsButtonHandle() {
        properties.setProperty("removeAllPlants", "true");
        synchronized (pauseLock){
            pauseLock.notify();
        }
    }


    public void refreshTimeButtonHandle() {
        properties.setProperty("refreshTime", refreshTime_textField.getText());
    }

}


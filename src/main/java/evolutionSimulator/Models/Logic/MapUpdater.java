package evolutionSimulator.Models.Logic;

import evolutionSimulator.Controllers.GUIUpdater;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Models.Species.Species;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.List;
import java.util.Properties;

public class MapUpdater extends Thread{
    private SingleCell[][] map;
    private int gridSize;
    private GUIUpdater guiUpdater;
    private int day;
    private Properties properties;

    public MapUpdater(SingleCell[][] map, GUIUpdater guiUpdater, Properties properties) {
        this.map = map;
        this.gridSize = map.length;
        this.guiUpdater = guiUpdater;
        this.properties = properties;
        this.day = Integer.parseInt(properties.getProperty("day"));
        new Thread(task).start();
    }

    Task task = new Task<Void>() {
        @Override
        protected Void call() {
            return null;
        }

        @Override public void run() {
            while (true) {
                for (int i = 0; i < gridSize; i++) {
                    for (int j = 0; j < gridSize; j++) {
                        if(!map[i][j].hasAnySpecies()){
                            continue;
                        }
                        List<Species> speciesList = map[i][j].getAllSpecies();
                        for (Species species : speciesList) {
                            species.copulate();
                            species.move();
                            species.updateVitality();
                        }
                    }
                }
                day++;
                if(day == 365){
                    day = 0;
                    int year = Integer.parseInt(properties.getProperty("year")) + 1;
                    properties.setProperty("year", String.valueOf(year));
                }
                properties.setProperty("day", String.valueOf(day));
                guiUpdater.update(map);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}

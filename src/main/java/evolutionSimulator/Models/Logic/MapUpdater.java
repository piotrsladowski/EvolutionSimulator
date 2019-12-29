package evolutionSimulator.Models.Logic;

import evolutionSimulator.Controllers.GUIUpdater;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Models.Species.Species;
import javafx.concurrent.Task;

import java.util.List;

public class MapUpdater extends Thread{
    private SingleCell[][] map;
    private int gridSize;
    private GUIUpdater guiUpdater;

    public MapUpdater(SingleCell[][] map, GUIUpdater guiUpdater) {
        this.map = map;
        this.gridSize = map.length;
        this.guiUpdater = guiUpdater;
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
                guiUpdater.update(map);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}

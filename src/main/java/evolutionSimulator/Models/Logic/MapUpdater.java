package evolutionSimulator.Models.Logic;

import evolutionSimulator.Controllers.GUIUpdater;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Models.Species.Species;
import javafx.concurrent.Task;

import java.util.ConcurrentModificationException;
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
                        List<Species> speciesList = map[i][j].getAllSpecies();
                        int n = 0;
                        while (speciesList.size() != 0) {
                            System.out.println(speciesList.size() + " n " + n);
                            if (speciesList.size() == n) {
                                System.out.println("Break M");
                                break;
                            } else {
                                System.out.println("Move M");
                                speciesList.get(n).move(map, i, j, gridSize);
                                n++;
                            }
                        }
                    }
                }
                System.out.println("Ruszone");
                for (int i = 0; i < gridSize; i++) {
                    for (int j = 0; j < gridSize; j++) {
                        List<Species> speciesList = map[i][j].getAllSpecies();
                        int n = 0;
                        while (speciesList.size() != 0) {
                            System.out.println(speciesList.size() + " n " + n);
                            if (speciesList.size() == n) {
                                System.out.println("Break M");
                                break;
                            } else {
                                n = n + speciesList.get(n).updateVitality(map, i, j);
                                System.out.println("Move M");
                            }
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

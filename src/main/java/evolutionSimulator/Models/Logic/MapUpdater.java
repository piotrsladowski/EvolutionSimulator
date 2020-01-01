package evolutionSimulator.Models.Logic;

import evolutionSimulator.Controllers.GUIUpdater;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Models.Species.Species;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Properties;

public class MapUpdater extends Thread{
    private SingleCell[][] map;
    private int gridSize;
    private GUIUpdater guiUpdater;
    private int day;
    private Properties properties;
    private Object pauseLock;
    private volatile boolean paused = false;

    public MapUpdater(SingleCell[][] map, GUIUpdater guiUpdater, Properties properties, Object pauseLock) {
        this.map = map;
        this.gridSize = map.length;
        this.guiUpdater = guiUpdater;
        this.properties = properties;
        this.pauseLock = pauseLock;
        this.day = Integer.parseInt(properties.getProperty("day"));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        /**
         * //TODO
         * Thats easiest, but the downside is that you wont get a graceful shutdown
         * (ie the threads will stop, you wont get a chance to perform resource
         * management etc, which may or may not be a problem for you).
         * https://stackoverflow.com/questions/14897194/stop-threads-before-close-my-javafx-program
         **/
        //new Thread(task).start();
    }

    Task task = new Task<Void>() {
        @Override
        protected Void call() {
            return null;
        }

        @Override public void run() {
            while (true) {
                if(properties.getProperty("paused").equals("true")) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException ex) {

                    }
                }
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
                cleardead();
                day++;
                if(day == 365){
                    day = 0;
                    int year = Integer.parseInt(properties.getProperty("year")) + 1;
                    properties.setProperty("year", String.valueOf(year));
                }
                properties.setProperty("day", String.valueOf(day));
                guiUpdater.update(map);
                System.out.println(day);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };

    public void cleardead(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                List<Species> speciesList = map[i][j].getAllSpecies();
                int n = 0;
                while (speciesList.size() != 0) {
                    if (speciesList.size() == n) {
                        break;
                    } else {
                        n = n + speciesList.get(n).updateVitality(map, i, j);
                    }
                }
            }
        }
    }
}






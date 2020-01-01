package evolutionSimulator.Models.Logic;

import evolutionSimulator.Controllers.GUIUpdater;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Models.Species.Plant;
import evolutionSimulator.Models.Species.Species;
import javafx.concurrent.Task;

import java.util.*;

public class MapUpdater extends Thread{
    private SingleCell[][] map;
    private int gridSize;
    private GUIUpdater guiUpdater;
    private int day;
    private Properties properties;
    private Object pauseLock;
    private volatile boolean paused = false;
    private Random random = new Random();

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
                if(properties.getProperty("spawnPlants").equals("true")) {
                    setPlant();
                }
                if(properties.getProperty("motionEnabled").equals("true")){
                    moveDay();
                }
                clearDead();
                if(properties.getProperty("eatingEnabled").equals("true")){
                    eatDay();
                }
                clearDead();
                System.out.println(day+" day " + speciesInt());
                day++;
                if(day == 365){
                    day = 0;
                    int year = Integer.parseInt(properties.getProperty("year")) + 1;
                    properties.setProperty("year", String.valueOf(year));
                }
                properties.setProperty("day", String.valueOf(day));
                guiUpdater.update(map);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    };
    public void setPlant(){
        ArrayList<String> plant = new ArrayList<>();
        plant.add("grass");
        plant.add("potato");
        for (int i =0; i<3; i++) {
            int type = random.nextInt(2);
            int x = random.nextInt(gridSize);
            int y = random.nextInt(gridSize);
            map[x][y].addSpeciesStartup(new Plant(1, plant.get(type)));
    }}
    public void eatDay(){for (int i = 0; i < gridSize; i++) {
        for (int j = 0; j < gridSize; j++) {
            List<Species> speciesList = map[i][j].getAllSpecies();
            int n = 0;
            while (speciesList.size() != 0) {
                if (speciesList.size() == n) {
                    break;
                } else {
                    speciesList.get(n).eat(speciesList);
                    n++;
                }
            }
        }
    }}
    public void moveDay(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                List<Species> speciesList = map[i][j].getAllSpecies();
                int n = 0;
                while (speciesList.size() != 0) {
                    if (speciesList.size() == n) {
                        break;
                    } else {
                        speciesList.get(n).move(map, i, j, gridSize);
                        n++;
                    }
                }
            }
    }}
    public void clearDead(){
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
    public int speciesInt(){
        int ilosc = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                List<Species> speciesList = map[i][j].getAllSpecies();
                ilosc += speciesList.size();
                }
            }
        return ilosc;
        }
}


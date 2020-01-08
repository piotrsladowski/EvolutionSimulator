package evolutionSimulator.Models.Logic;

import evolutionSimulator.Controllers.GUIUpdater;
import evolutionSimulator.Controllers.MyLogger;
import evolutionSimulator.Models.SingleCell;
import evolutionSimulator.Models.Species.Plant;
import evolutionSimulator.Models.Species.Species;
import javafx.concurrent.Task;

import java.util.*;

public class MapUpdater extends Thread{
    private final SingleCell[][] map;
    private final int gridSize;
    private final GUIUpdater guiUpdater;
    private int day;
    private final Properties properties;
    private final Object pauseLock;
    private final Random random = new Random();
    private final List<String[]> readPlants;

    public MapUpdater(SingleCell[][] map, GUIUpdater guiUpdater, Properties properties, Object pauseLock,List<String[]> readPlants) {
        this.map = map;
        this.gridSize = map.length;
        this.guiUpdater = guiUpdater;
        this.properties = properties;
        this.pauseLock = pauseLock;
        this.day = Integer.parseInt(properties.getProperty("day"));
        this.readPlants = readPlants;
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    final Task<Void> task = new Task<>() {
        @Override
        protected Void call() {
            return null;
        }

        @Override
        public void run() {
            // Log info about generated map
            int numOfNotEmptyCells = 0;
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    if (map[i][j].hasAnySpecies()) {
                        numOfNotEmptyCells++;
                    }
                }
            }
            MyLogger.newLogInfo("Map size: " + gridSize);
            MyLogger.newLogInfo("Not empty cells: " + numOfNotEmptyCells);
            //noinspection InfiniteLoopStatement
            while (true) {
                if (properties.getProperty("paused").equals("true")) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException ex) {
                        MyLogger.newLogSevere("Interrupted thread synchronization");
                    }
                }
                if (properties.getProperty("removeAllPlants").equals("true")) {
                    removeAllPlants();
                    properties.setProperty("removeAllPlants", "false");
                    continue;
                }
                if (properties.getProperty("spawnPlants").equals("true")) {
                    setPlant();
                }
                if (properties.getProperty("motionEnabled").equals("true")) {
                    moveDay();
                }
                clearDead();
                if (properties.getProperty("eatingEnabled").equals("true")) {
                    eatDay();
                }
                clearDead();
                if (properties.getProperty("procreationEnabled").equals("true")) {
                    copulateDay();
                }
                clearDead();
                System.out.println(day + " day " + speciesInt());
                day++;
                if (day == 365) {
                    day = 0;
                    int year = Integer.parseInt(properties.getProperty("year")) + 1;
                    properties.setProperty("year", String.valueOf(year));
                }
                properties.setProperty("day", String.valueOf(day));
                guiUpdater.update(map);
                int refreshTime = Integer.parseInt(properties.getProperty("refreshTime"));
                try {
                    Thread.sleep(refreshTime);
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
        int HP = 0;
        for (int i =0; i< gridSize*gridSize*0.05 ; i++) {
            int type = random.nextInt(2);
            int x = random.nextInt(gridSize);
            int y = random.nextInt(gridSize);
            for (String[] p: readPlants) {
                if (p[2].equals(plant.get(type))){
                    HP = Integer.parseInt(p[3]);
                }
            }
            map[x][y].addSpeciesStartup(new Plant(1, plant.get(type), HP));
        }
    }
    public void eatDay(){
        for (int i = 0; i < gridSize; i++) {
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
        }
    }
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
        }
    }

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
    public void copulateDay(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                List<Species> speciesList = map[i][j].getAllSpecies();
                int n = 0;
                while (speciesList.size() != 0) {
                    if (speciesList.size() == n) {
                        break;
                    } else {
                        speciesList.get(n).copulate(speciesList);
                        n++;
                    }
                }
            }
        }
    }

    private void removeAllPlants(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                List<Species> speciesList = map[i][j].getAllSpecies();
                for (Species species : speciesList){
                   if  (species.getClass().getName().equals("evolutionSimulator.Models.Species.Plant")){
                       species.setVitality(0);
                   }
                }
            }
        }
    }

    public int speciesInt(){
        int amount = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                List<Species> speciesList = map[i][j].getAllSpecies();
                for (Species species : speciesList) {
                    if (!species.getClass().getName().equals("evolutionSimulator.Models.Species.Plant")) {
                        amount += 1;
                    }
                }
            }
            }
        return amount;
        }

}


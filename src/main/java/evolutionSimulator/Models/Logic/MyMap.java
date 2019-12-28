package evolutionSimulator.Models.Logic;
import evolutionSimulator.Models.Species.Herbivore;
import evolutionSimulator.Models.Species.MeatEater;
import evolutionSimulator.Models.Species.Omnivore;
import evolutionSimulator.Models.Species.Plant;
import evolutionSimulator.Models.SingleCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyMap {
    private int gridSize;
    private SingleCell[][] map;
    private List<int[]> freeCells = new ArrayList<>();

    public MyMap(int gridSize) {
        this.gridSize = gridSize;
        map = new SingleCell[gridSize][gridSize];
    }

    private void createListOFFreeCells(){
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                freeCells.add(new int[]{i,j});
            }
        }
    }

    public SingleCell[][] generate(List<String[]> readAnimals, List<String[]> readPlants){
        Random rand = new Random();
        createListOFFreeCells();
        int size;

        // Insert randomly animals on the map
        for (String[] animal: readAnimals) {
            size = freeCells.size();
            String name = animal[2];
            int ID = Integer.parseInt(animal[0]);
            for (int i = 0; i < Integer.parseInt(animal[1]); i++) {
                int index = rand.nextInt(size-i);
                int[] cords = freeCells.get(index);
                freeCells.remove(index);
                String foodType = animal[3];
                int vitality = Integer.parseInt(animal[5]);
                if ("meat_eater".equals(foodType)) {
                    MeatEater m = new MeatEater(ID, name, ID, vitality);
                    SingleCell sc = new SingleCell();
                    sc.addSpeciesStartup(m);
                    map[cords[0]][cords[1]] = sc;
                }
                else if ("herbivore".equals(foodType)) {
                    Herbivore h = new Herbivore(ID, name, ID, vitality);
                    SingleCell sc = new SingleCell();
                    sc.addSpeciesStartup(h);
                    map[cords[0]][cords[1]] = sc;
                }
                else if ("omnivore".equals(foodType)) {
                    Omnivore o = new Omnivore(ID, name, ID, vitality);
                    SingleCell sc = new SingleCell();
                    sc.addSpeciesStartup(o);
                    map[cords[0]][cords[1]] = sc;
                }
            }
        }

        // Insert randomly plants on the map
        for (String[] plant: readPlants) {
            size = freeCells.size();
            String name = plant[2];
            int ID = Integer.parseInt(plant[0]);
            for (int i = 0; i < Integer.parseInt(plant[1]); i++) {
                int index = rand.nextInt(size-i);
                int[] cords = freeCells.get(index);
                freeCells.remove(index);
                SingleCell sc = new SingleCell();
                Plant p = new Plant(ID, name);
                //TODO set values
                sc.addSpeciesStartup(p);
                //sc.setPlant(p);
                map[cords[0]][cords[1]] = sc;

/*                MainWindow.cellGUIS[cords[0]][cords[1]] = cellGUI;
                MainWindow.stackPanes[cords[0]][cords[1]].getChildren().add(cellGUI);*/
            }
        }

        //generate cells not occupied by any species
        size = freeCells.size();
        for (int i = 0; i < size; i++) {
            int index = rand.nextInt(size-i);
            int[] cords = freeCells.get(index);
            freeCells.remove(index);
            SingleCell sc = new SingleCell();
            map[cords[0]][cords[1]] = sc;
        }
        return map;
    }
}

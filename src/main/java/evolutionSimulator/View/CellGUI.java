package evolutionSimulator.View;

import evolutionSimulator.Models.Species.Species;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class CellGUI extends Rectangle {
// --Commented out by Inspection START (04.01.2020 11:45):
//    //public int biomeID;
//    //public int[] animalIDArray;
//    //public int plantID;
//    private String name;
// --Commented out by Inspection STOP (04.01.2020 11:45)
    private List<Species> speciesList = new ArrayList<>();

    public CellGUI() {
        //this.animalIDArray = new int[]{0,0};
    }


    public void addAllSpecies(List<Species> speciesList){
        this.speciesList = speciesList;
    }

    public List<Species> getSpeciesList(){
        return speciesList;
    }
}

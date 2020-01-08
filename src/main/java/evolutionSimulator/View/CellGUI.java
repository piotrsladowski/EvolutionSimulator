package evolutionSimulator.View;

import evolutionSimulator.Models.Species.Species;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class CellGUI extends Rectangle {
    private List<Species> speciesList = new ArrayList<>();

    public void addAllSpecies(List<Species> speciesList){
        this.speciesList = speciesList;
    }

    public List<Species> getSpeciesList(){
        return speciesList;
    }
}

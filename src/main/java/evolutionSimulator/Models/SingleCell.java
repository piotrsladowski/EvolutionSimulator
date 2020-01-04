package evolutionSimulator.Models;

import evolutionSimulator.Models.Species.Species;

import java.util.ArrayList;
import java.util.List;

public class SingleCell {
    private final List<Species> speciesList = new ArrayList<>();

    public void addSpeciesStartup(Species species){
        this.speciesList.add(species);
    }

    public Species getSpecies(){
        return speciesList.get(0);
    }

    public List<Species> getAllSpecies(){
        return speciesList;
    }

    public boolean hasAnySpecies(){
        return speciesList.size() != 0;
    }

    public void delete(Species species){
        speciesList.remove(species);
    }
}

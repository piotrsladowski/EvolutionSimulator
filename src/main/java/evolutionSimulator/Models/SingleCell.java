package evolutionSimulator.Models;

import evolutionSimulator.Models.Species.Species;

import java.util.ArrayList;
import java.util.List;

public class SingleCell {
    private List<Species> speciesList = new ArrayList<>();

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
        if(speciesList.size() == 0)
            return false;
        else
            return true;
    }

    public void delete(Species species){
        speciesList.remove(species);
        //System.out.println("XD");
    }
}

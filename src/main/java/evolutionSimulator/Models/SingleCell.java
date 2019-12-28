package evolutionSimulator.Models;

import evolutionSimulator.Models.Species.Animals.Animal;
import evolutionSimulator.Models.Species.Plant;
import evolutionSimulator.Models.Species.Species;

import java.util.ArrayList;
import java.util.List;

public class SingleCell {
    private List<Species> animals = new ArrayList<>();
    private Plant plant;
    private List<Species> speciesList = new ArrayList<>();

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public void setAnimals(List<Species> animals) {
        this.animals = animals;
    }

    public void addAnimalStartup(Species animal){
        this.animals.add(animal);
    }

    public void addSpeciesStartup(Species species){
        this.speciesList.add(species);
    }

    public Species getSpecies(){
        return speciesList.get(0);
    }

    public boolean hasAnySpecies(){
        if(speciesList.size() == 0)
            return false;
        else
            return true;
    }


/*    public boolean isPlant(){
        if(this.plant == null)
            return false;
        return true;
    }
    public boolean isAnimal(){
        if(this.animals == null)
            return false;
        if(this.animals.size() == 0)
            return false;
        return true;
    }

    public String getName(){
        if(isPlant())
            return this.plant.getName();
        else if(isAnimal())
            return this.animals.get(0).getName();
        else
            return "";
    }*/
}

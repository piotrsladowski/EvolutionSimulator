package evolutionSimulator.Models;

import evolutionSimulator.Models.Species.Animals.Animal;
import evolutionSimulator.Models.Species.Plant;

import java.util.ArrayList;
import java.util.List;

public class SingleCell {
    private List<Animal> animals = new ArrayList<>();
    private Plant plant;

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public void addAnimalStartup(Animal animal){
        this.animals.add(animal);
    }

    public Plant getPlant(){
/*        if(this.plant == null)
            return null;*/
        return plant;
    }

    public Animal getAnimal(){
/*        if(this.animals == null)
            return null;
        if(this.animals.size() == 0)
            return null;*/
        return animals.get(0);
    }

    public boolean isPlant(){
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
    }
}

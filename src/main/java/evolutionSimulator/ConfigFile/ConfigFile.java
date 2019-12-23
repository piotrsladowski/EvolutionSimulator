package evolutionSimulator.ConfigFile;

import evolutionSimulator.Models.Species.Animals.Animal;
import evolutionSimulator.Models.Species.Animals.Herbivore;
import evolutionSimulator.Models.Species.Animals.MeatEater;
import evolutionSimulator.Models.Species.Animals.Omnivore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigFile {
    private String fileName;
    Map<String, String> generalProperties = new HashMap<String, String>();
    //Map<String, List<String>> animalsList = new HashMap<String, List<String>>();
    Map<String, Map<String, String>> animalsList = new HashMap<String, Map<String, String>>();

    public ConfigFile(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, String> getGeneralProperties() {
        return generalProperties;
    }

    public Map<String, Map<String, String>> getAnimalsList() {
        return animalsList;
    }
    private ArrayList<Integer> animalIDs = new ArrayList<>();

    private ArrayList<Animal> correctlyReadAnimals = new ArrayList<>();

    public ArrayList<Animal> getCorrectlyReadAnimals() {
        return correctlyReadAnimals;
    }

    public void load() throws IOException {
        List<String> usefulLines = new ArrayList<String>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            while (line != null) {
                if (line.length() == 0) {
                    line = br.readLine();
                    continue;
                }
                if (line.charAt(0) == '#') {
                    line = br.readLine();
                    continue;
                }
                usefulLines.add(line.toString());
                //sb.append(System.lineSeparator());
                line = br.readLine();
            }
        }

        sectionSort(usefulLines);
    }

    private void sectionSort(List<String> usefulLines) {
        int genStartIndex = usefulLines.indexOf("[General]") + 1;
        int genEndIndex = usefulLines.indexOf("[endGeneral]");
        for (int i = genStartIndex; i < genEndIndex; i++) {
            String parts[] = usefulLines.get(i).split("=", 2);
            if(parts[0].charAt(parts[0].length()-1) == ' '){
                parts[0] = parts[0].substring(0, parts[0].length()-1);
            }
            if(parts[1].charAt(0) == ' '){
                parts[1] = parts[1].substring(1);
            }
            generalProperties.put(parts[0], parts[1]);
        }



        int animalsStartIndex = usefulLines.indexOf("[AnimalsDeclaration]") + 1;
        int animalsEndIndex = usefulLines.indexOf("[endAnimalsDeclaration]");
        for (int i = animalsStartIndex; i < animalsEndIndex; i++) {
            String line = usefulLines.get(i);

            int endSingleAnimalDeclaration;
            if (line.charAt(0) == '<') {
                if (line.charAt(1) != '/') {
                    String animalClassName = line.substring(1, line.length() - 1);
                    endSingleAnimalDeclaration = usefulLines.indexOf("</" + animalClassName + ">");//TODO catch if end tag hasn't been found
                    List<String> sublist = usefulLines.subList(i+1,endSingleAnimalDeclaration);
                    readSingleAnimal(animalClassName, sublist);
                }
            }
        }

        //System.out.println(animalsList.get("Zwierze3").get("name"));
        createNewAnimalsObjects();

        int plantsStartIndex = usefulLines.indexOf("[PlantsDeclaration]") + 1;
        int plantsEndIndex = usefulLines.indexOf("[endPlantsDeclaration]");
/*        for (int i = plantsStartIndex; i < plantsEndIndex; i++) {
            String parts[] = usefulLines.get(i).split("=", 2);
            generalProperties.put(parts[0], parts[1]);
        }*/

    }

    private void readSingleAnimal(String animalClassName, List<String> singleAnimalProperties){
        Map<String, String> singleAnimalPropertiesMap = new HashMap<String, String>();
        for (String item:singleAnimalProperties) {
            String parts[] = item.split("=", 2);
            //remove whitespace before and after '='
            if(parts[0].charAt(parts[0].length()-1) == ' '){
                parts[0] = parts[0].substring(0, parts[0].length()-1);
            }
            if(parts[1].charAt(0) == ' '){
                parts[1] = parts[1].substring(1);
            }
            singleAnimalPropertiesMap.put(parts[0], parts[1]);
        }
        if(checkMandatoryProperties(animalClassName, singleAnimalPropertiesMap) == true){
            animalsList.put(animalClassName, singleAnimalPropertiesMap);
            System.out.println(animalClassName + " added");
        }
        else {
            System.out.println(animalClassName + " not added");
        }

    }

    private boolean checkMandatoryProperties(String animalClassName, Map<String, String> singleAnimalPropertiesMap){
        boolean result = true;
        if(singleAnimalPropertiesMap.containsKey("name") == false){
            System.out.println(animalClassName + " name not set");
            result = false;
        }
        if(singleAnimalPropertiesMap.containsKey("speed") == false){
            System.out.println(animalClassName + " speed not set");
            result = false;
        }
        if(singleAnimalPropertiesMap.containsKey("food_type") == false){
            System.out.println(animalClassName + " food_type not set");
            result = false;
        }
        return result;
    }

    private void createNewAnimalsObjects(){
        double allAnimals = Math.pow(Double.parseDouble(generalProperties.get("gridSize")),2) * Double.parseDouble(generalProperties.get("percentageOfAnimals")) * 0.01;
        int actualPercentage = 100;
            for (Map.Entry<String, Map<String, String>> entry : animalsList.entrySet()) {
                String key = entry.getKey();
                Map<String, String> value = entry.getValue();
                String foodType = value.get("food_type");
                String name = value.get("name");
                int speed = Integer.parseInt(value.get("speed"));
                int percentage = 0;
                try {
                    percentage = Integer.parseInt(value.get("percentageOfTotal"));
                } catch (NumberFormatException e) {
                    continue;
                    //percentage will be 0
                    //e.printStackTrace();
                }

                // assign ID to every created animal. Only animals with the same ID can copulate
                int ID;
                try {
                    ID = animalIDs.get(animalIDs.size() - 1) + 1;
                    animalIDs.add(ID);
                }catch (IndexOutOfBoundsException e){
                    ID = 1;
                    animalIDs.add(ID);
                }

                if (percentage > 100) {
                    percentage = 100;
                } else if (percentage < 0) {
                    percentage = 0;
                }
                if (actualPercentage - percentage < 0) {
                    percentage = actualPercentage;
                    actualPercentage = 0;
                }
                else{
                    actualPercentage -= percentage;
                }
                int number = (int) (allAnimals * percentage * 0.01);
                for (int i = 0; i < number; i++) {
                    if (foodType.equals("herbivore")) {
                        Herbivore h = new Herbivore(name, speed, ID);
                        correctlyReadAnimals.add(h);
                    }
                    else if (foodType.equals("meat_eater")) {
                        MeatEater m = new MeatEater(name, speed, ID);
                        correctlyReadAnimals.add(m);
                    }
                    else if (foodType.equals("omnivore")) {
                        Omnivore o = new Omnivore(name, speed, ID);
                        correctlyReadAnimals.add(o);
                    }
                }
                if(actualPercentage == 0){
                    break;
                }


/*            for (Map.Entry<String, String> childEntry : value.entrySet()){
                String childKey = childEntry.getKey();
                String childValue = childEntry.getValue();
            }*/
            }
    }
}

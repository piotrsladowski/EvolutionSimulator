package evolutionSimulator.Controllers;

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
    Map<String, Map<String, String>> animalsList = new HashMap<String, Map<String, String>>();
    Map<String, Map<String, String>> plantsList = new HashMap<String, Map<String, String>>();
    private List<String[]> validAnimals = new ArrayList<String[]>();
    private List<String[]> validPlants = new ArrayList<String[]>();
    private int ID;
    public ConfigFile(String fileName) {
        this.fileName = fileName;
    }

    public Map<String, String> getGeneralProperties() {
        return generalProperties;
    }

    public List<String[]> getValidAnimals() {
        return validAnimals;
    }

    public List<String[]> getValidPlants() {
        return validPlants;
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
                usefulLines.add(line);
                //sb.append(System.lineSeparator());
                line = br.readLine();
            }
        }

        sectionSort(usefulLines);
    }

    private void sectionSort(List<String> usefulLines) {

        //region generalProperties
        int genStartIndex = usefulLines.indexOf("[General]") + 1;
        int genEndIndex = usefulLines.indexOf("[endGeneral]");
        for (int i = genStartIndex; i < genEndIndex; i++) {
            String[] parts = usefulLines.get(i).split("=", 2);
            if (parts[0].charAt(parts[0].length() - 1) == ' ') {
                parts[0] = parts[0].substring(0, parts[0].length() - 1);
            }
            if (parts[1].charAt(0) == ' ') {
                parts[1] = parts[1].substring(1);
            }
            generalProperties.put(parts[0], parts[1]);
        }
        //endregion
        //TODO add only animals, not plants
        //region AnimalsProperties
        int animalsStartIndex = usefulLines.indexOf("[AnimalsDeclaration]") + 1;
        int animalsEndIndex = usefulLines.indexOf("[endAnimalsDeclaration]");
        for (int i = animalsStartIndex; i < animalsEndIndex; i++) {
            String line = usefulLines.get(i);

            int endSingleAnimalDeclaration;
            if (line.charAt(0) == '<') {
                if (line.charAt(1) != '/') {
                    String animalClassName = line.substring(1, line.length() - 1);
                    endSingleAnimalDeclaration = usefulLines.indexOf("</" + animalClassName + ">");//TODO catch if end tag hasn't been found
                    List<String> sublist = usefulLines.subList(i + 1, endSingleAnimalDeclaration);
                    Map<String, String> singleSpecies = readSingleSpecies(animalClassName, sublist);
                    if(checkAnimalMandatoryProperties(animalClassName, singleSpecies)){
                        animalsList.put(animalClassName, singleSpecies);
                        System.out.println(animalClassName + " added animal");
                    }
                }
            }
        }
        createNewAnimalsObjects();
        //endregion

        //region PlantsProperties
        int plantsStartIndex = usefulLines.indexOf("[PlantsDeclaration]") + 1;
        int plantsEndIndex = usefulLines.indexOf("[endPlantsDeclaration]");
        for (int i = plantsStartIndex; i < plantsEndIndex; i++) {
            String line = usefulLines.get(i);

            int endSinglePlantDeclaration;
            if (line.charAt(0) == '<') {
                if (line.charAt(1) != '/') {
                    String plantClassName = line.substring(1, line.length() - 1);
                    endSinglePlantDeclaration = usefulLines.indexOf("</" + plantClassName + ">");//TODO catch if end tag hasn't been found
                    List<String> sublist = usefulLines.subList(i + 1, endSinglePlantDeclaration);
                    Map<String, String> singleSpecies = readSingleSpecies(plantClassName, sublist);// parse all properties of single species
                    if(checkPlantMandatoryProperties(plantClassName, singleSpecies)){// don't add if not all mandatory properties were specified
                        plantsList.put(plantClassName, singleSpecies);
                        System.out.println(plantClassName + " added");
                    }
                }
            }
        }
        createNewPlantsObjects();
        //endregion
    }

    private Map<String, String> readSingleSpecies(String speciesClassName, List<String> singleSpeciesProperties) {
        Map<String, String> singleSpeciesPropertiesMap = new HashMap<String, String>();
        for (String item : singleSpeciesProperties) {
            String[] parts = item.split("=", 2);
            // remove whitespaces before '=' char
            parts[0] = parts[0].replaceAll("\\s", "");
            // remove single whitespace after '=' char
            if (parts[1].charAt(0) == ' ') {
                parts[1] = parts[1].substring(1);
            }
            singleSpeciesPropertiesMap.put(parts[0], parts[1]);
        }
        return singleSpeciesPropertiesMap;
    }

    private boolean checkAnimalMandatoryProperties(String animalClassName, Map<String, String> singleAnimalPropertiesMap) {
        boolean result = true;
        if (!singleAnimalPropertiesMap.containsKey("name")) {
            System.out.println(animalClassName + " name not set");
            result = false;
        }
        if (!singleAnimalPropertiesMap.containsKey("speed")) {
            System.out.println(animalClassName + " speed not set");
            result = false;
        }
        if (!singleAnimalPropertiesMap.containsKey("food_type")) {
            System.out.println(animalClassName + " food_type not set");
            result = false;
        }
        return result;
    }

    private boolean checkPlantMandatoryProperties(String animalClassName, Map<String, String> singleAnimalPropertiesMap) {
        boolean result = true;
        if (!singleAnimalPropertiesMap.containsKey("name")) {
            System.out.println(animalClassName + " name not set");
            result = false;
        }
        if (!singleAnimalPropertiesMap.containsKey("health_points")) {
            System.out.println(animalClassName + " health_points not set");
            result = false;
        }
        if (!singleAnimalPropertiesMap.containsKey("consume_time")) {
            System.out.println(animalClassName + " consume_time not set");
            result = false;
        }
        return result;
    }

    private void createNewAnimalsObjects() {
        double allAnimals = Math.pow(Double.parseDouble(generalProperties.get("gridSize")), 2) * Double.parseDouble(generalProperties.get("percentageOfAnimals")) * 0.01;
        int actualPercentage = 100;
        ID = 1;
        for (Map.Entry<String, Map<String, String>> entry : animalsList.entrySet()) {
            String[] newAnimal = new String[10];

            String key = entry.getKey();
            Map<String, String> value = entry.getValue();
            newAnimal[0] = String.valueOf(ID);
            newAnimal[2] = value.get("name");
            newAnimal[3] = value.get("food_type");
            newAnimal[4] = value.get("speed");
            newAnimal[5] = value.get("vitality");
            int percentage;
            try {
                percentage = Integer.parseInt(value.get("percentageOfTotal"));
            } catch (NumberFormatException e) {
                continue;
                //percentage will be 0
                //e.printStackTrace();
            }

            if (percentage > 100) {
                percentage = 100;
            } else if (percentage < 0) {
                percentage = 0;
            }
            if (actualPercentage - percentage < 0) {
                percentage = actualPercentage;
                actualPercentage = 0;
            } else {
                actualPercentage -= percentage;
            }
            int number = (int) (allAnimals * percentage * 0.01);

            newAnimal[1] = String.valueOf(number);
            validAnimals.add(newAnimal);
            ID++;
            if (actualPercentage == 0) {
                break;
            }
        }
    }

    private void createNewPlantsObjects(){
        double allPlants = Math.pow(Double.parseDouble(generalProperties.get("gridSize")), 2) * Double.parseDouble(generalProperties.get("percentageOfPlants")) * 0.01;

        for (Map.Entry<String, Map<String, String>> entry : plantsList.entrySet()) {
            Map<String, String> value = entry.getValue();
            int actualPercentage = 100;
            String[] newPlant = new String[10];
            newPlant[0] = String.valueOf(ID);
            newPlant[2] = value.get("name");
            newPlant[3] = value.get("health_points");
            newPlant[4] = value.get("consume_time");
            int percentage;
            try {
                percentage = Integer.parseInt(value.get("percentageOfTotal"));
            } catch (NumberFormatException e) {
                continue;
                //percentage will be 0
                //e.printStackTrace();
            }

            if (percentage > 100) {
                percentage = 100;
            } else if (percentage < 0) {
                percentage = 0;
            }
            if (actualPercentage - percentage < 0) {
                percentage = actualPercentage;
                actualPercentage = 0;
            } else {
                actualPercentage -= percentage;
            }
            int number = (int) (allPlants * percentage * 0.01);

            newPlant[1] = String.valueOf(number);
            validPlants.add(newPlant);
            ID++;
            if (actualPercentage == 0) {
                break;
            }
        }
    }
}

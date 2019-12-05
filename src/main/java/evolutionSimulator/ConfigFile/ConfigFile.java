package evolutionSimulator.ConfigFile;

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

        System.out.println(animalsList.get("Zwierze3").get("name"));

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

}
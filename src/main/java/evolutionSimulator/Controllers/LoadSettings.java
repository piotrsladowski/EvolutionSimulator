package evolutionSimulator.Controllers;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Options;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadSettings {
    private Options options;
    private Ini ini;
    private String fileName;

    public LoadSettings() {
        try {
            fileName = getClass().getResource("/app.ini").getFile();
        }catch (NullPointerException ex){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to read config file.");
        }
        try {
            ini = new Ini(new File(fileName));
            System.out.println("br");
        } catch (InvalidFileFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<String> sectionNames = ini.keySet();
        Map<String, String> map = ini.get("happy");
        System.out.println("List of Section Names: " + sectionNames + "\n");
    }

    public void loadGeneral(){

    }
}

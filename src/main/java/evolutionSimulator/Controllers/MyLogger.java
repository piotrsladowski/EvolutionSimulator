package evolutionSimulator.Controllers;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger extends Logger {
    private static Logger logger;
    private static FileHandler fileHandler;
    /**
     * Protected method to construct a logger for a named subsystem.
     * <p>
     * The logger will be initially configured with a null Level
     * and with useParentHandlers set to true.
     *
     * @param name               A name for the logger.  This should
     *                           be a dot-separated name and should normally
     *                           be based on the package name or class name
     *                           of the subsystem, such as java.net
     *                           or javax.swing.  It may be null for anonymous Loggers.
     * @param resourceBundleName name of ResourceBundle to be used for localizing
     *                           messages for this logger.  May be null if none
     *                           of the messages require localization.
     * @throws MissingResourceException if the resourceBundleName is non-null and
     *                                  no corresponding resource can be found.
     */
    public MyLogger(String name, String resourceBundleName) throws IOException {
        super(name, resourceBundleName);
        logger = MyLogger.getLogger(MyLogger.class.getName());
        fileHandler = new FileHandler("log.log", true);
        fileHandler.setFormatter(new SimpleFormatter());
        logger.addHandler(fileHandler);
    }

    public static void newLogSevere(String message){
        logger.log(Level.SEVERE, message);
    }

    public static void newLogWarning(String message){
        logger.log(Level.WARNING, message);
    }

    public static void newLogInfo(String message){
        logger.log(Level.INFO, message);
    }
}

package main.java.com.sd.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    private static Logger logger = LogManager.getLogger();
    public ConfigLoader() {}

    public static String loadValueFromConfig(String key){
        Properties properties = new Properties();
        try {
            properties.load(ConfigLoader.class.getResourceAsStream("/ChessEngine.properties"));
        } catch (IOException e) {
            logger.error("Failed to  load config");
            e.printStackTrace();
            System.exit(3);
        }
        return properties.getProperty(key); // somevalue
    }

    public static int loadValueAsInteger(String key) {
            return Integer.parseInt(loadValueFromConfig(key));
    }
}

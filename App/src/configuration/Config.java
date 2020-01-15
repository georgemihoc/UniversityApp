package configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static InputStream CONFIG_LOCATION = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");

    public static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(CONFIG_LOCATION);
            return properties;
        } catch (
                IOException e) {
            throw new RuntimeException("Cannot load config properties");
        }
    }
}
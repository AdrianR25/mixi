package dev.adrianr.mixi.misc;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class ConfigHandler {
    private final String configFilePath = "src/config.properties";
    private static Properties properties;
    public static String getProperty(String property) {
        if (properties == null) {
            loadFile();
        }
        return properties.getProperty(property);
    }

    private static void loadFile() {
        try {
            String configPath = new File(ConfigHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/') + "/config.properties";

            if (!Files.exists(Path.of(configPath))) {
                Utils.exportResource("/config.properties");
            }

            FileInputStream propsInput = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(propsInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

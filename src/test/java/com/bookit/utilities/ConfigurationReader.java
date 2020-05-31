package com.bookit.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {

    private static  Properties configFile;

    static {
        try {
            try (FileInputStream fileInputStream = new FileInputStream("configuration.properties")) {
                configFile = new Properties();
                configFile.load(fileInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file!");
        }
    }

    public static String getProperty(String propertyName) {

        return configFile.getProperty(propertyName);

    }

}

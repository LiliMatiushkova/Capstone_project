package org.project.holder;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyHolder {
    private final Properties properties = new Properties();

    {
        try (FileInputStream fis = new FileInputStream("src/test/resources/credentials.properties")) {
            properties.load(fis);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public String readProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
}

package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProp {

    private static final String TEST_DATA_RESOURCE = "TestData/TestData.properties";


    public static String getProperty(String key) {
        Properties prop = new Properties();

        try (InputStream input = LoadProp.class.getClassLoader().getResourceAsStream(TEST_DATA_RESOURCE)) {
            if (input == null) {
                throw new IllegalStateException("Unable to locate property resource: " + TEST_DATA_RESOURCE);
            }
            prop.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load property resource: " + TEST_DATA_RESOURCE, e);
        }
        return prop.getProperty(key);
    }
}

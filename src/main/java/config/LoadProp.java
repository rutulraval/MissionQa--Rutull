package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProp {

    static Properties prop;
    static FileInputStream input;
    public static String testData = "/src/test/java/TestData/TestData.properties";

    private static final File currentDirectory = new File(new File("").getAbsolutePath());


    public static String getProperty(String key) {
        prop = new Properties();

        try {
            input = new FileInputStream(currentDirectory + testData);
            prop.load(input);
            input.close();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load property file: " + currentDirectory + testData, e);
        }
        return prop.getProperty(key);
    }
}

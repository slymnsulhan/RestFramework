package api.utils;

import java.io.FileWriter;
import java.io.IOException;

public class tokenManager {
     static final String FILE_PATH = "Configuration.properties";

    public static void saveToken(String token) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write("\naccess.token=" + token);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getToken() {
        return ConfigurationReader.getProperty("access.token").toString();
    }
}

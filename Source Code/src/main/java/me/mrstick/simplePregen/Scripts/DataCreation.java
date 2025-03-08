package me.mrstick.simplePregen.Scripts;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class DataCreation {
    
    public static String dataFolder = "SimplePregen";

    public static void create() {

        File folder = new File("plugins/"+dataFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Creates Config.yaml file
        File config = new File(folder, "config.yml");
        if (!config.exists()) {
            createFile(config);
            copyDefaultConfig(config, "config.yml");
        }

        // Creates Messages.yml file
        File messages = new File(folder, "messages.yml");
        if (!messages.exists()) {
            createFile(messages);
            copyDefaultConfig(messages, "messages.yml");
        }

        File cache = new File(folder, "cache.json");
        if (!cache.exists()) {
            createFile(cache);
        }
    }

    private static void copyDefaultConfig(File configFile, String name) {
        try {
            // Load the default config from resources and replace!
            try (InputStream inputStream = DataCreation.class.getClassLoader().getResourceAsStream(name)) {
                if (inputStream != null) {
                    Files.copy(inputStream, configFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFile(File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
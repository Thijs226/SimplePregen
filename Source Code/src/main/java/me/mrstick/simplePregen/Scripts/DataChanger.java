package me.mrstick.simplePregen.Scripts;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class DataChanger {

    private final FileConfiguration reader;

    public DataChanger(FileConfiguration reader) {
        this.reader = reader;
    }

    public String ColorGrade(String path) {

        String message = reader.getString(path);

        message = message.replace("{prefix}", reader.getString("prefix"));
        message = message.replace("&", "§");
        return message;
    }
    public String SimpleColorGrade(String path) {

        String message = reader.getString(path);

        message = message.replace("&", "§");
        return message;
    }

    public List<String> MultiListPlaceholder(String path, List<String> placeholders, Map<String, String> values) {
        List<String> processedMessages = new ArrayList<>();
        List<String> msgs = reader.getStringList(path);
        for (String msg : msgs) {
            String message = msg;

            // Replace placeholders in each message
            for (String holder : placeholders) {
                message = message.replace(holder, values.get(holder));
            }

            // Replace additional placeholders
            message = message.replace("{prefix}", reader.getString("prefix"));
            message = message.replace("&", "§");

            // Add the processed message to the list
            processedMessages.add(message);
        }

        return processedMessages;
    }
    public List<String> SimpleMultiListPlaceholder(String path, List<String> placeholders, Map<String, String> values) {
        List<String> processedMessages = new ArrayList<>();
        List<String> msgs = reader.getStringList(path);
        for (String msg : msgs) {
            String message = msg;

            // Replace placeholders in each message
            for (String holder : placeholders) {
                message = message.replace(holder, values.get(holder));
            }

            // Replace additional placeholders
            message = message.replace("&", "§");

            // Add the processed message to the list
            processedMessages.add(message);
        }

        return processedMessages;
    }


    public String Placeholder(String path, String placeholder, String value) {

        String message = reader.getString(path);
        message = message.replace(placeholder, value);

        message = message.replace("{prefix}", reader.getString("prefix"));
        message = message.replace("&", "§");
        return message;
    }
    public String SimplePlaceholder(String path, String placeholder, String value) {

        String message = reader.getString(path);
        message = message.replace(placeholder, value);

        message = message.replace("&", "§");
        return message;
    }

    public List<String> ListColorgrade(String path) {
        List<String> processedMessages = new ArrayList<>();
        List<String> msgs = reader.getStringList(path);
        for (String msg : msgs) {
            String message = msg;

            message = message.replace("&", "§");

            // Add the processed message to the list
            processedMessages.add(message);
        }

        return processedMessages;
    }

    public String MultiPlaceholder(String path, List<String> placeholders, Map<String, String> values) {
        String message = reader.getString(path);

        // Replace placeholders in message
        for (String holder : placeholders) {
            message = message.replace(holder, values.get(holder));
        }

        // Replace additional placeholders
        message = message.replace("{prefix}", reader.getString("prefix"));
        message = message.replace("&", "§");
        return message;
    }

    public String SimpleMultiPlaceholder(String path, List<String> placeholders, Map<String, String> values) {
        String message = reader.getString(path);

        // Replace placeholders in message
        for (String holder : placeholders) {
            message = message.replace(holder, values.get(holder));
        }

        // Replace additional placeholders
        message = message.replace("&", "§");
        return message;
    }

    public List<String> SimpleListColorgrade(String path) {
        List<String> processedMessages = new ArrayList<>();
        List<String> msgs = reader.getStringList(path);
        for (String msg : msgs) {
            String message = msg;

            message = message.replace("&", "§");

            // Add the processed message to the list
            processedMessages.add(message);
        }

        return processedMessages;
    }


    public static List<String> StringToList(String str) {
        String[] elements = str.replaceAll("[\\[\\]']", "").split(",");

        List<String> list = new ArrayList<>();
        for (String element : elements) {
            list.add(element.trim());
        }

        return list;
    }

    public static List<String> GetKeys(FileConfiguration config, String path) {
        List<String> keysList = new ArrayList<>();

        // Check if the "effects" section exists
        if (config.isConfigurationSection(path)) {
            // Get the keys in the "effects" section
            Set<String> keys = config.getConfigurationSection(path).getKeys(false);
            keysList.addAll(keys); // Add all keys to the list
        }

        return keysList;
    }

    public static Map<String, String> toMap(List<String> keys, List<String> values) {

        Map<String, String> map = new HashMap<>();
        for (int i=0; i<keys.size(); i++) {
            map.put(keys.get(i), values.get(i));
        }
        return map;
    }

    public static List<String> toList(String value) {
        List<String> list = new ArrayList<>();
        list.add(value);
        return list;
    }

    public static List<String> toList(String value, String value2) {
        List<String> list = new ArrayList<>();
        list.add(value);
        list.add(value2);
        return list;
    }

    public static List<String> toList(String value, String value2, String value3) {
        List<String> list = new ArrayList<>();
        list.add(value);
        list.add(value2);
        list.add(value3);
        return list;
    }

    public static List<String> toList(String value, String value2, String value3, String value4) {
        List<String> list = new ArrayList<>();
        list.add(value);
        list.add(value2);
        list.add(value3);
        list.add(value4);
        return list;
    }

    public static List<String> toList(String value, String value2, String value3, String value4, String value5) {
        List<String> list = new ArrayList<>();
        list.add(value);
        list.add(value2);
        list.add(value3);
        list.add(value4);
        list.add(value5);
        return list;
    }

    public static List<String> toList(String value, String value2, String value3, String value4, String value5, String value6) {
        List<String> list = new ArrayList<>();
        list.add(value);
        list.add(value2);
        list.add(value3);
        list.add(value4);
        list.add(value5);
        list.add(value6);
        return list;
    }

    public static List<String> toList(String value, String value2, String value3, String value4, String value5, String value6, String value7, String value8) {
        List<String> list = new ArrayList<>();
        list.add(value);
        list.add(value2);
        list.add(value3);
        list.add(value4);
        list.add(value5);
        list.add(value6);
        list.add(value7);
        list.add(value8);
        return list;
    }

    public static List<String> toList(String value, String value2, String value3, String value4, String value5,
                                      String value6, String value7, String value8, String value9, String value10) {
        List<String> list = new ArrayList<>();
        list.add(value);
        list.add(value2);
        list.add(value3);
        list.add(value4);
        list.add(value5);
        list.add(value6);
        list.add(value7);
        list.add(value8);
        list.add(value9);
        list.add(value10);
        return list;
    }

}
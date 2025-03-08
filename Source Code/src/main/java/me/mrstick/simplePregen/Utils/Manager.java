package me.mrstick.simplePregen.Utils;

import me.mrstick.simplePregen.Scripts.DataChanger;
import me.mrstick.simplePregen.Scripts.DataCreation;
import me.mrstick.simplePregen.Scripts.JsonWriter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;

public class Manager {
    
    public static String folder = DataCreation.dataFolder;

    private static FileConfiguration config = null;
    private static FileConfiguration message = null;

    private static DataChanger ConfigChanger = null;
    private static DataChanger MessageChanger = null;

    public static JsonWriter CacheWriter = null;

    public static void Initialize() {
        config = YamlConfiguration.loadConfiguration(new File("plugins/"+folder+"/config.yml"));
        message = YamlConfiguration.loadConfiguration(new File("plugins/"+folder+"/messages.yml"));

        ConfigChanger = new DataChanger(config);
        MessageChanger = new DataChanger(message);

        CacheWriter = new JsonWriter("plugins/"+folder+"/cache.json", true);
    }


    public static FileConfiguration GetConfigReader() {return config;}
    public static FileConfiguration GetMessageReader() {return message;}

    public static DataChanger GetConfigChanger() {return ConfigChanger;}
    public static DataChanger GetMessageChanger() {return MessageChanger;}

}
package me.mrstick.simplePregen.Scripts;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Collections;
import java.util.Set;

public class YamlUtils {

    public static Set<String> getKeys(FileConfiguration config, String path) {
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section != null) {
            return section.getKeys(false); // Change to true for nested keys.
        }
        return Collections.emptySet();
    }
}

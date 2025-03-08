package me.mrstick.simplePregen;

import me.mrstick.simplePregen.Commands.simplepregen;
import me.mrstick.simplePregen.Scripts.DataCreation;
import me.mrstick.simplePregen.Utils.Manager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimplePregen extends JavaPlugin {

    @Override
    public void onLoad() {
        DataCreation.create();
        Manager.Initialize();
    }

    @Override
    public void onEnable() {

        getCommand("simplepregen").setExecutor(new simplepregen());
        getCommand("simplepregen").setTabCompleter(new simplepregen());

        Bukkit.getConsoleSender().sendMessage("§7[§bSimplePregen§7]§a Plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§7[§bSimplePregen§7]§c Plugin has been disabled!");
    }
}

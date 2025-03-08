package me.mrstick.simplePregen.Commands;

import me.mrstick.simplePregen.Scripts.YamlUtils;
import me.mrstick.simplePregen.Utils.CurseForgeDownloader;
import me.mrstick.simplePregen.Utils.Manager;
import me.mrstick.simplePregen.Utils.ZipExtractor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.codehaus.plexus.util.FileUtils;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class simplepregen implements CommandExecutor, TabCompleter {

    private final static Set<String> types = YamlUtils.getKeys(Manager.GetConfigReader(), "links");
    private final static boolean shouldRestart = Manager.GetConfigReader().getBoolean("restart-server");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        System.out.println(types);

        if (!sender.isOp()) {
            sender.sendMessage(Manager.GetMessageChanger().ColorGrade("permission-error"));
            return false;
        }

        if (args.length < 1) {
            sender.sendMessage(Manager.GetMessageChanger().ColorGrade("command-usage"));
            return false;
        }

        String slug = args[0];

        if (!types.contains(slug)) {
            sender.sendMessage(Manager.GetMessageChanger().ColorGrade("unknown-type"));
            return false;
        }

        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("starting"));
        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("kicking"));

        for (Player players : Bukkit.getOnlinePlayers()) {
            players.kick(Component.text(Manager.GetMessageChanger().ColorGrade("player-kicked")));
        }

        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("deleting-world"));
        File oldWorld = new File(Manager.GetConfigReader().getString("world-name"));
        try {
            FileUtils.deleteDirectory(oldWorld);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("deleting-world-failed"));
            throw new RuntimeException(e);
        }
        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("deleted-world"));

        String filename = null;
        try {
            filename = CurseForgeDownloader.downloadWorld(Manager.GetConfigReader().getString("links."+slug));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (filename == null) return false;

        File file = new File(filename);
        File folder = new File("world");
        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("extracting-world"));
        try {
            ZipExtractor.extractZipToFolder(file, folder);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("extracting-world-failed"));
            throw new RuntimeException(e);
        }
        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("extracted-world"));

        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("done"));
        file.delete();
        if (shouldRestart) {
            Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().ColorGrade("restarting"));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("simplepregen")) {
            if (strings.length == 1) {
                return YamlUtils.getKeys(Manager.GetConfigReader(), "links").stream().toList();
            }
        }

        return completions;
    }
}

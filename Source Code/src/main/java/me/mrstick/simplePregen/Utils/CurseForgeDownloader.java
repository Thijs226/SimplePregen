package me.mrstick.simplePregen.Utils;

import com.google.gson.*;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.*;
import java.util.*;

public class CurseForgeDownloader {

    private static final String API_KEY = Manager.GetConfigReader().getString("api-key"); // Use your actual API key

    public static String downloadWorld(String projectSlug) throws IOException {
        int projectId = getProjectId(projectSlug);

        if (projectId == -1) {
            Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().Placeholder("project-not-found",
                    "{project}", projectSlug));
            return null;
        }

        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().Placeholder("project-found",
                "{project}", projectSlug));

        JsonArray files = getProjectFiles(projectId);
        if (files.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().Placeholder("no-files-found",
                    "{project}", projectSlug));
            return null;
        }

        String oldFilename = (String) Manager.CacheWriter.Get("file");

        JsonObject randomFile;
        String fileName;

        // Keep picking random files until we find one that is different from oldFilename
        do {
            randomFile = files.get(new Random().nextInt(files.size())).getAsJsonObject();
            fileName = randomFile.get("fileName").getAsString();
        } while (oldFilename != null && fileName.equals(oldFilename) && files.size() > 1);

        int fileId = randomFile.get("id").getAsInt();

        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().Placeholder("downloading-file",
                "{file}", fileName));

        downloadFile(projectId, fileId, fileName);
        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().Placeholder("downloaded-file",
                "{file}", fileName));

        Manager.CacheWriter.Add("file", fileName);
        return fileName;
    }


    private static int getProjectId(String slug) throws IOException {
        URL url = new URL("https://api.curseforge.com/v1/mods/search?gameId=432&slug=" + URLEncoder.encode(slug, "UTF-8"));
        JsonObject response = getJsonResponse(url);
        JsonArray data = response.getAsJsonArray("data");
        if (data.isEmpty()) return -1;
        return data.get(0).getAsJsonObject().get("id").getAsInt();
    }

    private static String getProjectName(int projectId) throws IOException {
        URL url = new URL("https://api.curseforge.com/v1/mods/" + projectId);
        JsonObject response = getJsonResponse(url);
        return response.getAsJsonObject("data").get("name").getAsString();
    }

    private static JsonArray getProjectFiles(int projectId) throws IOException {
        URL url = new URL("https://api.curseforge.com/v1/mods/" + projectId + "/files");
        JsonObject response = getJsonResponse(url);
        return response.getAsJsonArray("data");
    }

    private static void downloadFile(int projectId, int fileId, String fileName) throws IOException {
        URL apiUrl = new URL("https://api.curseforge.com/v1/mods/" + projectId + "/files/" + fileId + "/download-url");

        HttpURLConnection con = (HttpURLConnection) apiUrl.openConnection();
        con.setConnectTimeout(60000);
        con.setReadTimeout(60000);
        con.setRequestMethod("GET");
        con.setRequestProperty("x-api-key", API_KEY);
        con.setRequestProperty("Accept", "application/json");

        if (con.getResponseCode() != 200) {
            Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().MultiPlaceholder("url-error",
                    List.of("{code}", "{message}"),
                    Map.of("{code}", String.valueOf(con.getResponseCode()),
                            "{message}", con.getResponseMessage())));
            return;
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String downloadUrl = JsonParser.parseString(response.toString()).getAsJsonObject().get("data").getAsString();
        Bukkit.getConsoleSender().sendMessage(Manager.GetMessageChanger().Placeholder("url-found",
                "{url}", downloadUrl));

        URL fileUrl = new URL(downloadUrl);
        HttpURLConnection fileCon = (HttpURLConnection) fileUrl.openConnection();
        int fileSize = fileCon.getContentLength();

        try (InputStream inFile = fileCon.getInputStream();
             FileOutputStream outFile = new FileOutputStream(fileName)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            int totalBytesRead = 0;
            int lastPrintedPercent = -1;

            while ((bytesRead = inFile.read(buffer)) != -1) {
                outFile.write(buffer, 0, bytesRead);
                totalBytesRead += bytesRead;

                int percent = (int) ((totalBytesRead / (double) fileSize) * 100);
                if (percent > lastPrintedPercent) {
                    lastPrintedPercent = percent;
                    printProgressBarBukkit(percent);
                }
            }
        }
    }

    private static void printProgressBarBukkit(int percent) {
        int barLength = 50;
        int filled = (int) ((percent / 100.0) * barLength);

        StringBuilder bar = new StringBuilder("§e[");
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filled ? "§a=" : "§7 ");
        }
        bar.append("§e] §a").append(percent).append("%");

        Bukkit.getConsoleSender().sendMessage(bar.toString());
    }


    private static void printProgressBar(int downloaded, int total) {
        int barLength = 50;
        double progress = (double) downloaded / total;
        int filled = (int) (progress * barLength);

        StringBuilder bar = new StringBuilder("\r[");
        for (int i = 0; i < barLength; i++) {
            bar.append(i < filled ? "=" : " ");
        }
        bar.append("] ").append(String.format("%.2f", progress * 100)).append("%");

        System.out.print(bar);
    }

    private static JsonObject getJsonResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("x-api-key", API_KEY);

        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed request: " + connection.getResponseCode() + " " + connection.getResponseMessage());
        }

        try (InputStreamReader reader = new InputStreamReader(connection.getInputStream())) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }
}

package me.mrstick.simplePregen.Utils;

import java.io.*;
import java.util.zip.*;

public class ZipExtractor {

    public static void extractZipToFolder(File zipFile, File outputFolder) throws IOException {
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry;

            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName();
                if (entryName.contains("/")) {
                    entryName = entryName.substring(entryName.indexOf("/") + 1);
                }

                if (entryName.isEmpty()) continue; // Skip empty entries (folder roots)

                File entryDestination = new File(outputFolder, entryName);
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    try (BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(entryDestination))) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = zipInputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
    }
}

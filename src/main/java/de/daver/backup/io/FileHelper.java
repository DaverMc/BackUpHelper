package de.daver.backup.io;

import de.daver.backup.LoggingHelper;
import de.daver.backup.io.visitor.CopyFileVisitor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

public class FileHelper {

    public static void copyFiles(Path sourceRoot, Path destinationRoot, Set<String> fileSuffixes, DestinationCreator creator) {
        try {
            Files.walkFileTree(sourceRoot, new CopyFileVisitor(sourceRoot, destinationRoot, fileSuffixes, creator));
        } catch (IOException e) {
            LoggingHelper.error(e);
        }
    }

    public static String getFileExtension(Path file) {
        var fileName = file.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        return dotIndex == -1 ? "" : fileName.substring(dotIndex + 1).toLowerCase();
    }

}

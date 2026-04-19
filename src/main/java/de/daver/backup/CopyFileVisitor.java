package de.daver.backup;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

public class CopyFileVisitor extends SimpleFileVisitor<Path> {

    private final Set<String> suffixes;
    private final Path rootDestination;

    public CopyFileVisitor(Path rootDestination, Set<String> suffixes) {
        this.suffixes = suffixes;
        this.rootDestination = rootDestination;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        LoggingHelper.debug("Processing %s", file.toString());
        for (var suffix : suffixes) {
            LoggingHelper.debug("Checking suffix \"%s\" for file: %s", suffix, file.toString());
            if (file.toString().endsWith("." + suffix)) {
                var destination = rootDestination.resolve(file.getFileName());
                Files.copy(file, destination);
                LoggingHelper.info("Copied file %s to %s", file.toString(), destination.toString());
                break;
            }
        }
        return FileVisitResult.CONTINUE;
    }
}

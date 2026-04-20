package de.daver.backup.io.visitor;

import de.daver.backup.io.FileHelper;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;

public class CopyFileVisitor extends SimpleFileVisitor<Path> {

    private final Set<String> suffixes;
    private final Path sourceRoot;
    private final Path rootDestination;
    private final DestinationCreator destinationCreator;
    private final FileVisitorProgress progress;

    public CopyFileVisitor(Path sourceRoot, Path rootDestination, Set<String> suffixes, DestinationCreator creator) {
        this.suffixes = suffixes;
        this.sourceRoot = sourceRoot;
        this.rootDestination = rootDestination;
        this.destinationCreator = creator;
        this.progress = new FileVisitorProgress(sourceRoot, this::containsExtension);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(!containsExtension(file)) return FileVisitResult.CONTINUE;
        var destination = destinationCreator.getDestination(sourceRoot, file, rootDestination);

        var destinationDir = destination.getParent();
        if(destinationDir != null && Files.notExists(destinationDir)) {
            Files.createDirectories(destinationDir);
        }

        Files.copy(file, destination);
        progress.step();
        return FileVisitResult.CONTINUE;
    }

    boolean containsExtension(Path file) {
        String fileExtension = FileHelper.getFileExtension(file);
        return suffixes.contains(fileExtension);
    }

}

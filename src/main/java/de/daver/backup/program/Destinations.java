package de.daver.backup.program;

import de.daver.backup.io.visitor.DestinationCreator;

import java.io.IOException;
import java.nio.file.Path;

public enum Destinations implements DestinationCreator {

    PLAIN((_, sourceFile, rootDestination) -> rootDestination.resolve(sourceFile.getFileName())),
    FLAT_PACKETS((sourceRoot, sourceFile, rootDestination) -> {
        var relativized = sourceRoot.relativize(sourceFile);
        var parent = relativized.getParent();

        var dirName = (parent == null) ? "root" : parent.toString()
                                                  .replace("\\", "_")
                                                  .replace("/", "_");

        return rootDestination.resolve(dirName).resolve(sourceFile.getFileName());
    });

    private final DestinationCreator creator;

    Destinations(DestinationCreator creator) {
        this.creator = creator;
    }

    @Override
    public Path getDestination(Path sourceRoot, Path sourceFile, Path rootDestination) throws IOException {
        return creator.getDestination(sourceRoot, sourceFile, rootDestination);
    }
}

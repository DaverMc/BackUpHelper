package de.daver.backup.io;

import java.io.IOException;
import java.nio.file.Path;

public interface DestinationCreator {

    Path getDestination(Path sourceRoot, Path sourceFile, Path rootDestination) throws IOException;

}

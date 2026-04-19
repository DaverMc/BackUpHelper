package de.daver.backup.program;

import de.daver.backup.CopyFileVisitor;
import de.daver.backup.LoggingHelper;
import de.daver.backup.util.ConsoleInput;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public record FastCopyAction() implements Action {

    @Override
    public boolean run() {
        var source = ConsoleInput.readInput("Source directory to search from: ", Path::of);
        var destination = ConsoleInput.readInput("Destination directory to copy to: ", Path::of);
        var suffixes = ConsoleInput.readInput("Suffixes to copy example (png,docx,xlsx,...): ",
                s -> new HashSet<>(Arrays.asList(s.split(","))));

        copyFiles(source, destination, suffixes);
        return false;
    }

    void copyFiles(Path sourceRoot, Path destinationRoot, Set<String> fileSuffixes) {
        try {
            Files.walkFileTree(sourceRoot, new CopyFileVisitor(destinationRoot, fileSuffixes));
        } catch (IOException e) {
            LoggingHelper.error(e);
        }
    }
}

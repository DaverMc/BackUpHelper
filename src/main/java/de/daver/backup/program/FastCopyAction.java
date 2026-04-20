package de.daver.backup.program;

import de.daver.backup.io.DestinationCreator;
import de.daver.backup.io.FileHelper;
import de.daver.backup.util.Console;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record FastCopyAction(DestinationCreator creator) implements Action {

    public static Action plain() {
        return new FastCopyAction((_, sourceFile, rootDestination) -> rootDestination
                .resolve(sourceFile.getFileName()));
    }

    public static Action parentDir() {
        return new FastCopyAction((sourceRoot, sourceFile, rootDestination) -> {
            var relativized = sourceRoot.relativize(sourceFile);
            var parent = relativized.getParent();

            var dirName = (parent == null) ? "root" : parent.toString()
                    .replace("\\", "_")
                    .replace("/", "_");

            return rootDestination.resolve(dirName).resolve(sourceFile.getFileName());
        });
    }

    @Override
    public boolean run() {
        var source = Console.readInput("Source directory to search from: ", Path::of);
        var destination = Console.readInput("Destination directory to copy to: ", Path::of);
        var suffixes = Console.readInput("Suffixes to copy example (png,docx,xlsx,...): ",
                s -> Arrays.stream(s.split(","))
                        .map(String::trim)
                        .filter(Predicate.not(String::isBlank))
                        .collect(Collectors.toSet()));

        FileHelper.copyFiles(source, destination, suffixes, creator);
        return false;
    }


}

package de.daver.backup.program;

import de.daver.backup.function.Deserializer;
import de.daver.backup.io.visitor.DestinationCreator;
import de.daver.backup.io.FileHelper;
import de.daver.backup.util.Console;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public record FastCopyAction(DestinationCreator creator) implements Action {

    public static final Deserializer<Set<String>> SUFFIX_DESERIALIZER = string -> Arrays.stream(string.split(","))
            .map(String::trim)
            .filter(Predicate.not(String::isBlank))
            .collect(Collectors.toSet());

    @Override
    public boolean run() {
        var source = Console.readInput("Source directory to search from: ", Path::of);
        var destination = Console.readInput("Destination directory to copy to: ", Path::of);
        var suffixes = Console.readInput("Suffixes to copy example (png,docx,xlsx,...): ", SUFFIX_DESERIALIZER);

        FileHelper.copyFiles(source, destination, suffixes, creator);
        return false;
    }

}

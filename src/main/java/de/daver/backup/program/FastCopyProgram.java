package de.daver.backup.program;

import de.daver.backup.CopyFileVisitor;
import de.daver.backup.LoggingHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FastCopyProgram extends SimpleProgram {

    public FastCopyProgram() {
        super("fastCopy");
    }

    @Override
    public void run() {
        var source = readLn("Source directory to search from: ", Path::of);
        var destination = readLn("Destination directory to copy to: ", Path::of);
        var suffixes = readLn("Suffixes to copy example (png,docx,xlsx,...): ",
                s -> new HashSet<>(Arrays.asList(s.split(","))));

        copyFiles(source, destination, suffixes);
        stop();
    }

    void copyFiles(Path sourceRoot, Path destinationRoot, Set<String> fileSuffixes) {
        try {
            Files.walkFileTree(sourceRoot, new CopyFileVisitor(destinationRoot, fileSuffixes));
        } catch (IOException e) {
            LoggingHelper.error(e);
        }
    }
}

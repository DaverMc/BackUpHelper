import de.daver.backup.CopyFileVisitor;
import de.daver.backup.LoggingHelper;

void main() {
    final String VERSION = "1.0";

    LoggingHelper.info("Welcome to BackUp-Helper v.%s", VERSION);

    var source = readLn("Source directory to search from: ", Path::of);
    var destination = readLn("Destination directory to copy to: ", Path::of);
    var suffixes = readLn("Suffixes to copy example (png,docx,xlsx,...): ",
            s -> new HashSet<>(Arrays.asList(s.split(","))));

    copyFiles(source, destination, suffixes);
}


<T> T readLn(String prompt, Function<String, T> deserializer) {
    return deserializer.apply(IO.readln(prompt));
}


void copyFiles(Path sourceRoot, Path destinationRoot, Set<String> fileSuffixes) {
    try {
        Files.walkFileTree(sourceRoot, new CopyFileVisitor(destinationRoot, fileSuffixes));
    } catch (IOException e) {
        LoggingHelper.error(e);
    }
}


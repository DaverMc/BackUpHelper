import java.util.logging.Level;
import java.util.logging.Logger;

private final String VERSION = "1.0";

void main() {
    LoggingHelper.info("Welcome to BackUp-Helper v.%s", VERSION);

    var src = readLn("Source directory to search from: ", Path::of);
    var dest = readLn("Destination directory to copy to: ", Path::of);
    var suffixes = readLn("Suffixes to copy example (png,docx,xlsx,...): ",
            s -> new HashSet<>(Arrays.asList(s.split(","))));

    copyFiles(src, dest, suffixes);
}


<T> T readLn(String prompt, Function<String, T> deserializer) {
    return deserializer.apply(IO.readln(prompt));
}


void copyFiles(Path rootSrc, Path rootDest, Set<String> fileSuffixes) {
    try {
        Files.walkFileTree(rootSrc, new CopyFileVisitor(rootDest, fileSuffixes));
    } catch (IOException e) {
        LoggingHelper.error(e);
    }
}

private static class CopyFileVisitor extends SimpleFileVisitor<Path> {

    private final Set<String> suffixes;
    private final Path rootDest;

    CopyFileVisitor(Path rootDest, Set<String> suffixes) {
        this.suffixes = suffixes;
        this.rootDest = rootDest;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        for(var suffix : suffixes) {
            if(file.endsWith("." + suffix)) {
                var destination = rootDest.resolve(file);
                Files.copy(file, destination);
                LoggingHelper.info("Copied file %s to %s", file.toString(), destination.toString());
                break;
            }
        }
        return FileVisitResult.CONTINUE;
    }
}

public static class LoggingHelper {

    static final Logger LOGGER = Logger.getLogger("BackUp-Helper");

    static void info(String message, Object... values) {
        log(Level.INFO, message, values);
    }

    static void error(Throwable throwable) {
        StringBuilder errorMessage = new StringBuilder(throwable.getClass().getName());
        errorMessage.append(" (\"").append(throwable.getMessage()).append("\")");
        for(var element : throwable.getStackTrace()) {
            errorMessage.append("\n\t")
                    .append(element.toString());
        }
        print(Level.SEVERE, errorMessage.toString());
    }

    private static void print(Level level, String message) {
        //LOGGER.log(level, message);
        IO.println("[" + level.getName() + "]: " + message);
    }

    static void log(Level level, String formatted, Object... values) {
        print(level, String.format(formatted, values));
    }

}
import de.daver.backup.LoggingHelper;
import de.daver.backup.program.*;

void main() {
    final String VERSION = "1.0";

    LoggingHelper.info("Welcome to BackUp-Helper v.%s", VERSION);

    var programManager = registerPrograms();

    while(programManager.run()) {}

    LoggingHelper.info("Shutting down BackUp-Helper, See you :)");
}

ProgramManager registerPrograms() {
    var pm = new ProgramManager();

    pm.register("plain", new FastCopyAction(Destinations.PLAIN));
    pm.register("flat", new FastCopyAction(Destinations.FLAT_PACKETS));
    pm.register("config", new ConfigurableCopyAction());

    return pm;
}


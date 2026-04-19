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
    var pm = new ProgramManager("switcher");
    pm.register("switcher", new SwitcherProgram(pm));

    pm.register("fastCopy", new FastCopyAction());
    pm.register("parent", new ParentModeAction());

    return pm;
}


import de.daver.backup.LoggingHelper;
import de.daver.backup.program.FastCopyProgram;
import de.daver.backup.program.ParentModeProgram;
import de.daver.backup.program.ProgramManager;
import de.daver.backup.program.SwitcherProgram;

void main() {
    final String VERSION = "1.0";

    LoggingHelper.info("Welcome to BackUp-Helper v.%s", VERSION);

    var programManager = new ProgramManager();
    registerPrograms(programManager);

    while(programManager.run()) {}

    LoggingHelper.info("Shutting down BackUp-Helper, See you :)");
}

void registerPrograms(ProgramManager pm) {
    pm.register(new SwitcherProgram(pm));
    pm.register(new FastCopyProgram());
    pm.register(new ParentModeProgram());
}


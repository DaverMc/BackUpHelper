package de.daver.backup.program;

import de.daver.backup.LoggingHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class ProgramManager {

    private final Map<String, Program> availablePrograms;
    private final AtomicReference<Program> activeProgram;

    public ProgramManager() {
        availablePrograms = new ConcurrentHashMap<>();
        activeProgram = new AtomicReference<>(null);
    }

    public void register(Program program) {
        availablePrograms.put(program.name(), program);
    }

    public void switchTo(String id) {
        var program = availablePrograms.get(id);
        if(program == null) {
            LoggingHelper.info("Program with id %s isn't registered!", id);
            return;
        }
        var oldProgram = activeProgram.getAndSet(program);
        if(oldProgram != null) oldProgram.stop();
        program.start();
        LoggingHelper.debug("Switched to program %s", program.name());
    }

    public boolean run() {
        final var active = activeProgram.get();
        if(active != null && active.isRunning()) {
            active.run();
            return true;
        }
        switchTo("switcher");
        return true;
    }

}

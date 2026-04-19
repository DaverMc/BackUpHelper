package de.daver.backup.program;

import de.daver.backup.LoggingHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ProgramManager {

    private final Map<String, Program> availablePrograms;
    private final AtomicReference<Program> activeProgram;
    private final AtomicBoolean active;
    private final String defaultProgramId;

    public ProgramManager(String defaultProgramId) {
        this.defaultProgramId = defaultProgramId;
        availablePrograms = new ConcurrentHashMap<>();
        activeProgram = new AtomicReference<>(null);
        active = new AtomicBoolean(true);
    }

    public void register(String name, Action action) {
        var program = new Program(name, new AtomicBoolean(false), action);
        availablePrograms.put(name, program);
    }


    public void switchTo(String id) {
        var program = availablePrograms.get(id);
        if(program == null) {
            LoggingHelper.info("Program with id %s isn't registered!", id);
            return;
        }
        var oldProgram = activeProgram.getAndSet(program);
        if(oldProgram != null) stopProgram(oldProgram);
        startProgram(program);
        LoggingHelper.debug("Switched to program %s", program.name());
    }

    private void startProgram(Program program) {
        if(program.running().compareAndSet(false, true)) {
            LoggingHelper.info("Starting program %s", program.name());
        } else {
            LoggingHelper.info("Program %s is already running!", program.name());
        }
    }

    private void stopProgram(Program program) {
        if(program.running().compareAndSet(true, false)) {
            LoggingHelper.info("Stopping program %s", program.name());
        } else {
            LoggingHelper.info("Program %s is not running!", program.name());
        }
    }

    public boolean run() {
        final var active = activeProgram.get();
        if(runProgram(active)) return true;
        if(!this.active.get()) return false;
        switchTo(defaultProgramId);
        return true;
    }

    private boolean runProgram(Program program) {
        if(program == null || !program.running().get()) return false;

        try {
            return program.action().run();
        } catch (Exception e) {
            LoggingHelper.error(e);
            return false;
        }

    }

    void deactivate() {
        if(active.compareAndSet(true, false)) {
            stopProgram(this.activeProgram.get());
            LoggingHelper.debug("Deactivating ProgramManager...");
        } else {
            LoggingHelper.info("ProgramManager is already deactivated!");
        }
    }

    private record Program(String name, AtomicBoolean running, Action action) {}
}

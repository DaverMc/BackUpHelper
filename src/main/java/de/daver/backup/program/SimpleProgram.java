package de.daver.backup.program;

import de.daver.backup.LoggingHelper;
import de.daver.backup.function.Deserializer;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleProgram implements Program {

    private final String name;
    private final AtomicBoolean running;

    public SimpleProgram(String name) {
        this.name = name;
        this.running = new AtomicBoolean(false);
    }


    @Override
    public boolean isRunning() {
        return running.get();
    }

    @Override
    public void run() {

    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void start() {
        if(running.compareAndSet(false, true)) {
            LoggingHelper.info("Starting program %s", name);
        } else {
            LoggingHelper.info("Program %s is already running!", name);
        }
    }

    @Override
    public void stop() {
        if(running.compareAndSet(true, false)) {
            LoggingHelper.info("Stopping program %s", name);
        } else {
            LoggingHelper.info("Program %s is not running!", name);
        }
    }

    protected <T> T readLn(String prompt, Deserializer<T> deserializer) {
        return deserializer.deserialize(IO.readln(prompt));
    }

}

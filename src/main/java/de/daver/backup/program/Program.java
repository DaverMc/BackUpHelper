package de.daver.backup.program;

public interface Program {

    boolean isRunning();

    void run();

    String name();

    void start();

    void stop();
}

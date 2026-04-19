package de.daver.backup.program;

public interface Action {

    //True if it should be repeated
    boolean run() throws Exception;
}


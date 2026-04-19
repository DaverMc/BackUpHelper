package de.daver.backup.program;


public record ParentModeAction() implements Action {

    @Override
    public boolean run() throws Exception{
        Thread.sleep(100);
        return false;
    }
}

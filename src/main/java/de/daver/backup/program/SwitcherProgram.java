package de.daver.backup.program;

public class SwitcherProgram extends SimpleProgram {

    private final ProgramManager manager;

    public SwitcherProgram(ProgramManager manager) {
        super("switcher");
        this.manager = manager;
    }

    @Override
    public void run() {
        switch (readLn("Please choose your program (0 = exit, 1 = fast, 2 = parent mode)", Integer::parseInt)) {
            case 0 -> manager.deactivate();
            case 1 -> manager.switchTo("fastCopy");
            case 2 -> manager.switchTo("parent");
        }
    }
}

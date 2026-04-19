package de.daver.backup.program;

import de.daver.backup.util.ConsoleInput;

public record SwitcherProgram(ProgramManager manager) implements Action {

    @Override
    public boolean run() {
        switch (ConsoleInput.readInput("Please choose your program (0 = exit, 1 = fast, 2 = parent mode)", Integer::parseInt)) {
            case 0 -> manager.deactivate();
            case 1 -> manager.switchTo("fastCopy");
            case 2 -> manager.switchTo("parent");
        }
        return true;
    }
}

package de.daver.backup.program;

import de.daver.backup.util.Console;

public record SwitcherProgram(ProgramManager manager) implements Action {

    private static final String STOP_PROGRAM_ID = "stop";

    @Override
    public boolean run() {
        StringBuilder builder = new StringBuilder("Please choose from these commands: \n");
        for(var subCommand : manager.getActionKeys()) {
            if(manager.isDefaultId(subCommand)) continue;
            appendSubCommand(subCommand, builder);
        }
        appendSubCommand(STOP_PROGRAM_ID, builder);
        var programId = Console.readString(builder.toString());
        if(programId.equals(STOP_PROGRAM_ID)) {
            manager.deactivate();
            return false;
        }
        manager.switchTo(programId);
        return true;
    }

    private void appendSubCommand(String subCommand, StringBuilder builder) {
        builder.append("-  ").append(subCommand).append("\n");
    }
}

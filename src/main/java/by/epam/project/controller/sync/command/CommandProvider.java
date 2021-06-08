package by.epam.project.controller.sync.command;

import by.epam.project.controller.sync.command.impl.EmptyCommand;

/**
 * The type Command provider.
 */
public class CommandProvider {
    private CommandProvider() {
    }

    /**
     * Provide command command.
     *
     * @param command the command
     * @return the command
     */
    public static Command provideCommand(String command) {
        Command currentCommand;

        if (command == null || command.isEmpty()) {
            return new EmptyCommand();
        }

        try {
            currentCommand = CommandType.valueOf(command.toUpperCase()).getCommand();
        } catch (IllegalArgumentException exp) {
            currentCommand = new EmptyCommand();
        }

        return currentCommand;
    }
}
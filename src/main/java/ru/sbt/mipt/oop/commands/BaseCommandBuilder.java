package ru.sbt.mipt.oop.commands;

public class BaseCommandBuilder implements CommandBuilder {
    private CommandBuilder next;

    @Override
    public CommandBuilder setNext(CommandBuilder next) {
        this.next = next;
        return this;
    }

    @Override
    public Command build(String commandType) {
        return passOrNull(commandType);
    }

    public Command passOrNull(String commandType) {
        if (next != null)
            return next.build(commandType);
        else
            return null;
    }
}

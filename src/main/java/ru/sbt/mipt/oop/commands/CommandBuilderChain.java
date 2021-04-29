package ru.sbt.mipt.oop.commands;

public class CommandBuilderChain {
    private final BaseCommandBuilder[] commandBuilders;

    public CommandBuilderChain(BaseCommandBuilder[] commandBuilders) {
        this.commandBuilders = commandBuilders;
    }

    public CommandBuilder commandBuilder() {
        CommandBuilder builderChain = new BaseCommandBuilder();
        for (BaseCommandBuilder commandBuilder : commandBuilders) {
            builderChain = builderChain.setNext(commandBuilder);
        }
        return builderChain;
    }
}

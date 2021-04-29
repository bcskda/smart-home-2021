package ru.sbt.mipt.oop.commands;

import java.util.Collection;

public class CommandBuilderChain {
    private final Collection<BaseCommandBuilder> commandBuilders;

    public CommandBuilderChain(Collection<BaseCommandBuilder> commandBuilders) {
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

package ru.sbt.mipt.oop.commands;

public interface CommandBuilder {
    Command build(String commandType);

    CommandBuilder setNext(CommandBuilder next);
}

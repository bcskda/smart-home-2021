package ru.sbt.mipt.oop.commands;

interface CommandBuilder {
    Command build(String commandType);

    CommandBuilder setNext(CommandBuilder next);
}

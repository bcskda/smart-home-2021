package ru.sbt.mipt.oop.commands.handlers;

import ru.sbt.mipt.oop.commands.SensorCommand;

public interface SensorCommandHandler {
    void handleCommand(SensorCommand command);
}

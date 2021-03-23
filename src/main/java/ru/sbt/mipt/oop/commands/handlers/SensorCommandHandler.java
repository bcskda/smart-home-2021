package ru.sbt.mipt.oop.commands.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.commands.SensorCommand;

public interface SensorCommandHandler {
    Action handleCommand(SensorCommand command);
}

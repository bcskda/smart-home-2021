package ru.sbt.mipt.oop.commands.legacy.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.commands.legacy.SensorCommand;

public interface SensorCommandHandler {
    Action handleCommand(SensorCommand command);
}

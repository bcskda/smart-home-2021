package ru.sbt.mipt.oop.commands.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.commands.SensorCommand;

public class LogCommandHandler implements SensorCommandHandler {
    @Override
    public Action handleCommand(SensorCommand command) {
        System.err.println(command);
        return null;
    }
}

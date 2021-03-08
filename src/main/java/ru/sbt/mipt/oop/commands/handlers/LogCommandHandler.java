package ru.sbt.mipt.oop.commands.handlers;

import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

public class LogCommandHandler implements SensorCommandHandler {
    @Override
    public void handleCommand(SensorCommand command) throws IllegalArgumentException {
        System.err.println(command);
    }
}

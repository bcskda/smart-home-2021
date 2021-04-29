package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.legacy.SensorCommand;

public interface CommandSender {
    void sendCommand(SensorCommand command);
}

package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.SensorCommand;

public interface CommandSender {
    void sendCommand(SensorCommand command);
}

package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.SensorCommand;

public interface SmartHomeController {
    void sendCommand(SensorCommand command);

    SmartHome getHome();
}

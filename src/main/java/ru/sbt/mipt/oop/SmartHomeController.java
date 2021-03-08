package ru.sbt.mipt.oop;

public interface SmartHomeController {
    void sendCommand(SensorCommand command);

    SmartHome getHome();
}

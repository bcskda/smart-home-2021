package ru.sbt.mipt.oop;

public class SmartHomeControllerStub implements SmartHomeController {
    private final SmartHome smartHome;

    public SmartHomeControllerStub(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void sendCommand(SensorCommand command) {
        System.out.println("Pretent we're sending command " + command);
    }

    @Override
    public SmartHome getHome() {
        return smartHome;
    }
}

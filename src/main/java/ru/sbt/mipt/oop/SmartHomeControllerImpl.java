package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;

import java.util.List;

public class SmartHomeControllerImpl implements SmartHomeController {
    private final SmartHome smartHome;
    private final List<SensorCommandHandler> commandHandlers;

    public SmartHomeControllerImpl(SmartHome smartHome,
                                   List<SensorCommandHandler> commandHandlers) {
        this.smartHome = smartHome;
        this.commandHandlers = commandHandlers;
    }

    @Override
    public void sendCommand(SensorCommand command) {
        for (SensorCommandHandler handler : commandHandlers) {
            try {
                handler.handleCommand(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public SmartHome getHome() {
        return smartHome;
    }
}

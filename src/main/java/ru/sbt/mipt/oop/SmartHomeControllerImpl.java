package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

import java.util.Map;

public class SmartHomeControllerImpl implements SmartHomeController {
    private final SmartHome smartHome;
    private final Map<CommandType, SensorCommandHandler> commandHandlers;
    private final SensorCommandHandler defaultHandler;

    public SmartHomeControllerImpl(SmartHome smartHome,
                                   Map<CommandType, SensorCommandHandler> commandHandlers,
                                   SensorCommandHandler defaultHandler) {
        this.smartHome = smartHome;
        this.commandHandlers = commandHandlers;
        this.defaultHandler = defaultHandler;
    }

    public SmartHomeControllerImpl(SmartHome smartHome, Map<CommandType, SensorCommandHandler> commandHandlers) {
        this(smartHome, commandHandlers, null);
    }

    @Override
    public void sendCommand(SensorCommand command) {
        System.out.println("Got command: " + command);
        SensorCommandHandler handler = commandHandlers.getOrDefault(command.getType(), defaultHandler);
        if (handler == null) {
            throw new IllegalArgumentException(
                    "Missing handler for SensorCommandType " + command.getType().toString());
        }
        handler.handleCommand(command);
    }

    @Override
    public SmartHome getHome() {
        return smartHome;
    }
}

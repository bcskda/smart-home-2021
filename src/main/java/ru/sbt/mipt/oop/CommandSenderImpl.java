package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.legacy.SensorCommand;
import ru.sbt.mipt.oop.commands.legacy.handlers.SensorCommandHandler;

import java.util.List;

public class CommandSenderImpl implements CommandSender {
    private final SmartHome smartHome;
    private final List<SensorCommandHandler> commandHandlers;

    public CommandSenderImpl(SmartHome smartHome, List<SensorCommandHandler> commandHandlers) {
        this.smartHome = smartHome;
        this.commandHandlers = commandHandlers;
    }

    @Override
    public void sendCommand(SensorCommand command) {
        for (SensorCommandHandler handler : commandHandlers) {
            try {
                Action action = handler.handleCommand(command);
                if (action != null)
                    smartHome.execute(action);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

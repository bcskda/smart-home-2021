package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;

import java.util.List;

public class CommandSenderImpl implements CommandSender {
    private final List<SensorCommandHandler> commandHandlers;

    public CommandSenderImpl(List<SensorCommandHandler> commandHandlers) {
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
}

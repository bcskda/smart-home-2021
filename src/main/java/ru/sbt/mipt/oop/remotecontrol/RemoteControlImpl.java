package ru.sbt.mipt.oop.remotecontrol;

import rc.RemoteControl;
import ru.sbt.mipt.oop.commands.Command;

import java.util.HashMap;
import java.util.Map;

public class RemoteControlImpl implements RemoteControl {
    private final String id;
    public final Map<String, Command> commands = new HashMap<>();

    public RemoteControlImpl(String id) {
        this.id = id;
    }

    public void addCommand(String buttonCode, Command command) {
        commands.put(buttonCode, command);
    }

    @Override
    public void onButtonPressed(String buttonCode) {
        if (commands.containsKey(buttonCode))
            commands.get(buttonCode).execute();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RemoteControlImpl{" +
                "id='" + id + '\'' +
                '}';
    }
}

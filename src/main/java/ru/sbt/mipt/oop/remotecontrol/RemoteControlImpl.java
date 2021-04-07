package ru.sbt.mipt.oop.remotecontrol;

import rc.RemoteControl;

import java.util.HashMap;
import java.util.Map;

public class RemoteControlImpl implements RemoteControl {
    private final String id;
    public final Map<String, Runnable> commands = new HashMap<>();

    public RemoteControlImpl(String id) {
        this.id = id;
    }

    public void addCommand(String buttonCode, Runnable command) {
        commands.put(buttonCode, command);
    }

    @Override
    public void onButtonPressed(String buttonCode) {
        if (! commands.containsKey(buttonCode))
            return;
        else
            commands.get(buttonCode).run();
    }

    @Override
    public String toString() {
        return "RemoteControlImpl{" +
                "id='" + id + '\'' +
                '}';
    }
}

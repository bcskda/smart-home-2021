package ru.sbt.mipt.oop;

import rc.RemoteControl;

import java.util.*;

public class RemoteControlRegistryMock {
    private final List<String> allowedButtons = Arrays.asList(
            "A", "B", "C", "D", "1", "2", "3", "4"
    );
    private final Map<String, RemoteControl> registry = new HashMap<>();

    /**
     * Register remote control with id rcId.
     * When button on a real remote control device is pressed this library will call remoteControl.onButtonPressed(...).
     */
    public void registerRemoteControl(RemoteControl remoteControl, String rcId) {
        registry.put(rcId, remoteControl);
    }

    public void run(int iterations) {
        List<String> rcIds = new ArrayList<>(registry.keySet());
        Random prng = new Random(42);
        for (int i = 0; i < iterations; i++) {
            int rcIndex = prng.nextInt(registry.size());
            int buttonIndex = prng.nextInt(allowedButtons.size());
            call(rcIds.get(rcIndex), allowedButtons.get(buttonIndex));
        }
    }

    public void call(String id, String button) {
        registry.get(id).onButtonPressed(button);
    }
}

package ru.sbt.mipt.oop;

import java.util.Map;

public class SensorEventLoop {
    SensorEventSource eventSource;
    Map<SensorEventType, SensorEventHandler> eventHandlers;

    SensorEventLoop(SensorEventSource eventSource,
                    Map<SensorEventType, SensorEventHandler> eventHandlers) {
        this.eventSource = eventSource;
        this.eventHandlers = eventHandlers;
    }

    /**
     * @return whether event was available
     * @throws IllegalArgumentException - if handler missing, or forwarded from handler
     */
    boolean runOnce() throws IllegalArgumentException {
        SensorEvent event = eventSource.next();
        if (event != null) {
            handleEvent(event);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @throws IllegalArgumentException - if handler missing, or forwarded from handler
     */
    private void handleEvent(SensorEvent event) throws IllegalArgumentException {
        System.out.println("Got event: " + event);
        SensorEventHandler handler = eventHandlers.get(event.getType());
        if (handler == null) {
            throw new IllegalArgumentException(
                    "Missing handler for SensorEventType " + event.getType().toString());
        }
        handler.handleEvent(event);
    }
}

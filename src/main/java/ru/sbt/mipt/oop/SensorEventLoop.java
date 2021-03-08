package ru.sbt.mipt.oop;

import java.util.Map;

public class SensorEventLoop {
    SensorEventSource eventSource;
    Map<SensorEventType, SensorEventHandler> eventHandlers;
    SensorEventHandler defaultHandler;

    public SensorEventLoop(SensorEventSource eventSource,
                    Map<SensorEventType, SensorEventHandler> eventHandlers,
                    SensorEventHandler defaultHandler) {
        this.eventSource = eventSource;
        this.eventHandlers = eventHandlers;
        this.defaultHandler = defaultHandler;
    }

    public SensorEventLoop(SensorEventSource eventSource, Map<SensorEventType, SensorEventHandler> eventHandlers) {
        this(eventSource, eventHandlers, null);
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
        SensorEventHandler handler = eventHandlers.getOrDefault(event.getType(), defaultHandler);
        if (handler == null) {
            throw new IllegalArgumentException(
                    "Missing handler for SensorEventType " + event.getType().toString());
        }
        handler.handleEvent(event);
    }
}

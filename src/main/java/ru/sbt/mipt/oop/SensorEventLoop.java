package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.sources.SensorEventSource;

import java.util.List;
import java.util.Map;

public class SensorEventLoop {
    SensorEventSource eventSource;
    List<SensorEventHandler> eventHandlers;

    public SensorEventLoop(SensorEventSource eventSource,
                           List<SensorEventHandler> eventHandlers) {
        this.eventSource = eventSource;
        this.eventHandlers = eventHandlers;
    }

    /**
     * @return whether event was available
     */
    boolean runOnce() {
        SensorEvent event = eventSource.next();
        if (event != null) {
            handleEvent(event);
            return true;
        } else {
            return false;
        }
    }

    void runCatchSuppress() {
        // начинаем цикл обработки событий
        boolean hasEvents = true;
        while (hasEvents) {
            hasEvents = runOnce();
        }
    }

    private void handleEvent(SensorEvent event) {
        for (SensorEventHandler eventHandler : eventHandlers) {
            try {
                eventHandler.handleEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

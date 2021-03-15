package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.sources.SensorEventSource;

import java.util.List;
import java.util.Map;

public class SensorEventLoop {
    SmartHome smartHome;
    SensorEventSource eventSource;
    List<SensorEventHandler> eventHandlers;

    public SensorEventLoop(SmartHome smartHome,
                           SensorEventSource eventSource,
                           List<SensorEventHandler> eventHandlers) {
        this.smartHome = smartHome;
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
                Action action = eventHandler.handleEvent(event);
                if (action != null)
                    smartHome.execute(action);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

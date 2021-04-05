package ru.sbt.mipt.oop.ccadapt;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;

import java.util.Locale;

public class EventHandlerAdaptor implements com.coolcompany.smarthome.events.EventHandler {
    private final EventHandler nativeHandler;
    private final SmartHome smartHome;

    public EventHandlerAdaptor(EventHandler nativeHandler, SmartHome smartHome) {
        this.nativeHandler = nativeHandler;
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(CCSensorEvent foreignEvent) {
        Event event = adaptEvent(foreignEvent);
        Action action = nativeHandler.handleEvent(event);
        if (action != null)
            smartHome.execute(action);
    }

    private static Event adaptEvent(CCSensorEvent event) {
        EventType eventType = adaptEventType(event.getEventType());
        return new SensorEvent(eventType, event.getObjectId());
    }

    private static EventType adaptEventType(String ccEventType) {
        // Для данной версии либы работает
        return EventType.valueOf(ccEventType
                .toUpperCase(Locale.ROOT)
                .replace("IS", "_"));
    }
}

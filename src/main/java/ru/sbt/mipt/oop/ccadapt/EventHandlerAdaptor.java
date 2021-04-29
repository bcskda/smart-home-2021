package ru.sbt.mipt.oop.ccadapt;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;

import java.util.Map;

public class EventHandlerAdaptor implements com.coolcompany.smarthome.events.EventHandler {
    private final Map<String, EventType> eventTypeMap;
    private final EventHandler nativeHandler;
    private final SmartHome smartHome;

    public EventHandlerAdaptor(EventHandler nativeHandler, SmartHome smartHome,
                               Map<String, EventType> eventTypeMap) {
        this.nativeHandler = nativeHandler;
        this.smartHome = smartHome;
        this.eventTypeMap = eventTypeMap;
    }

    @Override
    public void handleEvent(CCSensorEvent foreignEvent) {
        Event event = adaptEvent(foreignEvent);
        if (event == null)
            return;
        Action action = nativeHandler.handleEvent(event);
        if (action == null)
            return;
        smartHome.execute(action);
    }

    private Event adaptEvent(CCSensorEvent event) {
        EventType eventType = adaptEventType(event.getEventType());
        if (eventType == null)
            return null;
        else
            return new SensorEvent(eventType, event.getObjectId());
    }

    private EventType adaptEventType(String ccEventType) {
        return eventTypeMap.get(ccEventType);
    }
}

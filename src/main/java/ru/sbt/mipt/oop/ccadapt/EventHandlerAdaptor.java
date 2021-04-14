package ru.sbt.mipt.oop.ccadapt;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public class EventHandlerAdaptor implements com.coolcompany.smarthome.events.EventHandler {
    private static final Map<String, EventType> eventTypeMap = new HashMap<>();
    private final EventHandler nativeHandler;
    private final SmartHome smartHome;

    static {
        eventTypeMap.put("LightIsOn", EventType.LIGHT_ON);
        eventTypeMap.put("LightIsOff", EventType.LIGHT_OFF);
        eventTypeMap.put("DoorIsOpen", EventType.DOOR_OPEN);
        eventTypeMap.put("DoorIsClosed", EventType.DOOR_CLOSED);
    }

    public EventHandlerAdaptor(EventHandler nativeHandler, SmartHome smartHome) {
        this.nativeHandler = nativeHandler;
        this.smartHome = smartHome;
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

    private static Event adaptEvent(CCSensorEvent event) {
        EventType eventType = adaptEventType(event.getEventType());
        if (eventType == null)
            return null;
        else
            return new SensorEvent(eventType, event.getObjectId());
    }

    private static EventType adaptEventType(String ccEventType) {
        return eventTypeMap.get(ccEventType);
    }
}

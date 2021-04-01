package ru.sbt.mipt.oop.ccadapt;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;

public class EventAdaptor {
    public static Event adapt(CCSensorEvent event) {
        EventType eventType = EventTypeAdaptor.adapt(event.getEventType());
        return new SensorEvent(eventType, event.getObjectId());
    }
}

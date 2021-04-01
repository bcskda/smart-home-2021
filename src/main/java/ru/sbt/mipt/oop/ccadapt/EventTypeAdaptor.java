package ru.sbt.mipt.oop.ccadapt;

import ru.sbt.mipt.oop.events.EventType;

import java.util.Locale;

public class EventTypeAdaptor {
    public static EventType adapt(String ccEventType) {
        // Для данной версии либы работает
        return EventType.valueOf(ccEventType
                .toUpperCase(Locale.ROOT)
                .replace("IS", "_"));
    }
}

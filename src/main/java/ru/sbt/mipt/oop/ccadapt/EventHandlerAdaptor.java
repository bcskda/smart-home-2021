package ru.sbt.mipt.oop.ccadapt;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;

import java.util.Locale;
import java.util.function.Consumer;

public class EventHandlerAdaptor implements com.coolcompany.smarthome.events.EventHandler {
    private final EventHandler nativeHandler;
    private final Consumer<Action> actionConsumer;

    public EventHandlerAdaptor(EventHandler nativeHandler, Consumer<Action> actionConsumer) {
        this.nativeHandler = nativeHandler;
        this.actionConsumer = actionConsumer;
    }

    @Override
    public void handleEvent(CCSensorEvent foreignEvent) {
        Event event = adaptEvent(foreignEvent);
        actionConsumer.accept(nativeHandler.handleEvent(event));
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

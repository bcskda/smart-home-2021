package ru.sbt.mipt.oop.ccadapt;

import com.coolcompany.smarthome.events.CCSensorEvent;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.handlers.EventHandler;

import java.util.function.Consumer;

public class EventHandlerAdaptor implements com.coolcompany.smarthome.events.EventHandler {
    EventHandler actual;
    Consumer<Action> actionConsumer;

    public EventHandlerAdaptor(EventHandler actual, Consumer<Action> actionConsumer) {
        this.actual = actual;
        this.actionConsumer = actionConsumer;
    }

    @Override
    public void handleEvent(CCSensorEvent foreignEvent) {
        Event event = EventAdaptor.adapt(foreignEvent);
        actionConsumer.accept(actual.handleEvent(event));
    }
}

package ru.sbt.mipt.oop.ccadapt;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.events.handlers.EventHandler;

import java.util.function.Consumer;

public class EventHandlerAdaptorFactory {
    Consumer<Action> actionConsumer;

    public EventHandlerAdaptorFactory(Consumer<Action> actionConsumer) {
        this.actionConsumer = actionConsumer;
    }

    public com.coolcompany.smarthome.events.EventHandler adapt(EventHandler handler) {
        return new EventHandlerAdaptor(handler, actionConsumer);
    }
}

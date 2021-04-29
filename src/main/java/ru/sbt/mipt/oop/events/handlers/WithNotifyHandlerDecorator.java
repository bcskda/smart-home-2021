package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.NotificationSender;
import ru.sbt.mipt.oop.actions.PairActionDecorator;
import ru.sbt.mipt.oop.actions.RunOnceDecorator;
import ru.sbt.mipt.oop.events.Event;

public class WithNotifyHandlerDecorator implements EventHandler {
    NotificationSender notificationSender;
    EventHandler wrapped;

    public WithNotifyHandlerDecorator(NotificationSender notificationSender, EventHandler wrapped) {
        this.notificationSender = notificationSender;
        this.wrapped = wrapped;
    }

    @Override
    public Action handleEvent(Event event) {
        Action underlying = wrapped.handleEvent(event);
        return new PairActionDecorator(new RunOnceDecorator(notificationSender::sendSms), underlying);
    }
}

package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AlarmStateStale implements AlarmState {
    SmartHome smartHome;
    Collection<SensorEventHandler> eventHandlers;

    public AlarmStateStale(SmartHome smartHome, Collection<SensorEventHandler> eventHandlers) {
        this.smartHome = smartHome;
        this.eventHandlers = eventHandlers;
    }

    @Override
    public AlarmState Activate(String code) {
        System.out.println("Activating alarm from state: stale");
        return new AlarmStateArmed(smartHome, eventHandlers, code);
    }

    @Override
    public AlarmState Deactivate(String code) {
        throw new IllegalStateException("Cannot deactivate alarm in state: stale");
    }

    @Override
    public AlarmState Trigger() {
        System.out.println("Triggering alarm from state: stale");
        return new AlarmStateFiring(smartHome, eventHandlers);
    }

    @Override
    public Action handleEvent(SensorEvent event) {
        List<Action> actions = eventHandlers.stream().map(
                handler -> handler.handleEvent(event)
        ).collect(Collectors.toList());
        return component -> {
            for (Action action : actions) {
                action.execute(component);
            }
        };
    }
}

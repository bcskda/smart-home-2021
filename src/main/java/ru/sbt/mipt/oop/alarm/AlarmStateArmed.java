package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

import java.util.Collection;

public class AlarmStateArmed implements AlarmState {
    SmartHome smartHome;
    Collection<SensorEventHandler> eventHandlers;
    Alarm alarm;
    String code;

    public AlarmStateArmed(SmartHome smartHome, Collection<SensorEventHandler> eventHandlers,
                           Alarm alarm, String code) {
        this.smartHome = smartHome;
        this.eventHandlers = eventHandlers;
        this.alarm = alarm;
        this.code = code;
    }

    @Override
    public AlarmState Activate(String code) {
        throw new IllegalStateException("Cannot activate alarm in state: armed");
    }

    @Override
    public AlarmState Deactivate(String code) {
        if (this.code.equals(code)) {
            System.out.println("Deactivating alarm from state: armed");
            return new AlarmStateStale(smartHome, eventHandlers, alarm);
        } else {
            return this.Trigger();
        }
    }

    @Override
    public AlarmState Trigger() {
        System.out.println("Triggering alarm from state: armed");
        return new AlarmStateFiring(smartHome, eventHandlers, alarm);
    }

    @Override
    public Action handleEvent(SensorEvent event) {
        alarm.Trigger();
        return null;
    }
}

package ru.sbt.mipt.oop.alarm;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

import java.util.Collection;

class AlarmStateFiring implements Alarm.AlarmState {
    SmartHome smartHome;
    Collection<SensorEventHandler> eventHandlers;
    Alarm alarm;

    public AlarmStateFiring(SmartHome smartHome, Collection<SensorEventHandler> eventHandlers,
                            Alarm alarm) {
        this.smartHome = smartHome;
        this.eventHandlers = eventHandlers;
        this.alarm = alarm;
        ToggleAllLights();
        SendSms();
    }

    @Override
    public Alarm.AlarmState Activate(String code) {
        throw new IllegalStateException("Cannot activate alarm in state: firing");
    }

    @Override
    public Alarm.AlarmState Deactivate(String code) {
        throw new IllegalStateException("Cannot deactivate alarm in state: firing");
    }

    @Override
    public Alarm.AlarmState Trigger() {
        throw new IllegalStateException("Cannot trigger alarm in state: firing");
    }

    @Override
    public Action handleEvent(SensorEvent event) {
        ToggleAllLights();
        SendSms();
        return null;
    }

    void SendSms() {
        System.out.println("Sending SMS");
    }

    void ToggleAllLights() {
        smartHome.execute(component -> {
            if (! (component instanceof Light))
                return;
            Light light = (Light) component;
            light.setOn(!light.isOn());
        });
    }
}

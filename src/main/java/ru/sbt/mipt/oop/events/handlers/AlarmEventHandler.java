package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.HomeComponent;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.AlarmStateArmed;
import ru.sbt.mipt.oop.alarm.AlarmStateFiring;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

public class AlarmEventHandler implements EventHandler {
    Alarm alarm;

    public AlarmEventHandler(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public Action handleEvent(Event event) {
        if (event instanceof AlarmEvent)
            return onAlarmEvent((AlarmEvent) event);
        else if (event instanceof SensorEvent)
            return onSensorEvent((SensorEvent) event);
        else return null;
    }

    Action onAlarmEvent(AlarmEvent event) {
        updateAlarmState(event);
        if (isFiring()) {
            return onAlarmFiring();
        }
        return null;
    }

    Action onSensorEvent(SensorEvent event) {
        if (isFiring() || isArmed()) {
            return onAlarmFiring();
        }
        return null;
    }

    boolean isFiring() {
        return alarm.getState() == AlarmStateFiring.class;
    }

    boolean isArmed() {
        return alarm.getState() == AlarmStateArmed.class;
    }

    void updateAlarmState(AlarmEvent event){
        switch (event.getType()) {
            case ALARM_ACTIVATE:
                alarm.activate(event.getCode());
                break;
            case ALARM_DEACTIVATE:
                alarm.deactivate(event.getCode());
                break;
            default:
                break;
        }
    }

    public Action onAlarmFiring() {
        final Action actSms = makeSendSms();
        final Action actLight = makeToggleAllLights();
        return component -> {
            actSms.execute(component);
            actLight.execute(component);
        };
    }

    void sendSms() {
        System.out.println("Sending SMS");
    }

    Action makeSendSms() {
        return new Action() {
            boolean sent = false;

            @Override
            public void execute(HomeComponent component) {
                if (!sent) {
                    sendSms();
                    sent = true;
                }
            }
        };
    }

    Action makeToggleAllLights() {
        return component -> {
            if (!(component instanceof Light))
                return;
            Light light = (Light) component;
            light.setOn(!light.isOn());
        };
    }
}

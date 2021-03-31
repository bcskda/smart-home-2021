package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Actionable;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.alarm.Alarm;
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
        if (alarm.isFiring()) {
            return onAlarmFiring();
        }
        return null;
    }

    Action onSensorEvent(SensorEvent event) {
        if (alarm.isFiring() || alarm.isArmed()) {
            return onAlarmFiring();
        }
        return null;
    }

    void updateAlarmState(AlarmEvent event) {
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
            component.execute(actSms);
            component.execute(actLight);
        };
    }

    void sendSms() {
        System.out.println("Sending SMS");
    }

    Action makeSendSms() {
        return new Action() {
            boolean sent = false;

            @Override
            public void execute(Actionable component) {
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

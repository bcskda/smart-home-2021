package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.HomeComponent;
import ru.sbt.mipt.oop.Light;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.alarm.AlarmStateFiring;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;

public class AlarmEventHandler implements EventHandler {
    Alarm alarm;

    public AlarmEventHandler(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public Action handleEvent(Event event) {
        if (!(event instanceof AlarmEvent))
            return null;
        return onAlarmEvent((AlarmEvent) event);
    }

    Action onAlarmEvent(AlarmEvent event) {
        updateAlarmState(event);
        if (alarm.getState() == AlarmStateFiring.class) {
            return onAlarmFiring();
        }
        return null;
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

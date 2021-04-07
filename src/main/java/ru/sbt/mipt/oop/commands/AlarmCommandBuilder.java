package ru.sbt.mipt.oop.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.alarm.Alarm;

@Component
public class AlarmCommandBuilder extends BaseCommandBuilder {
    @Autowired SmartHome smartHome;
    @Autowired Alarm alarm;

    @Override
    public Command build(String commandType) {
        if ("triggerAlarm".equals(commandType))
            return alarm::trigger;
        else if ("activateAlarm".equals(commandType))
            return () -> alarm.activate(smartHome.getDefaultAlarmCode());
        else
            return passOrNull(commandType);
    }
}

package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.alarm.Alarm;

public class AlarmCommandBuilder extends BaseCommandBuilder {
    private final SmartHome smartHome;
    private final Alarm alarm;

    public AlarmCommandBuilder(SmartHome smartHome, Alarm alarm) {
        this.smartHome = smartHome;
        this.alarm = alarm;
    }

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

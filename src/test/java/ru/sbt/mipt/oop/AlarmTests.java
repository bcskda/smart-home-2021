package ru.sbt.mipt.oop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.events.AlarmSensorEvent;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.handlers.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class AlarmTests {
    SmartHome smartHome;
    List<SensorEventHandler> usualHandlers;
    Alarm alarm;

    @Before
    public void setUp() {
        InputStream stream = getClass().getResourceAsStream("test-smart-home.json");
        smartHome = new JsonConfigurationReader(stream).readSmartHome();
        usualHandlers = Arrays.asList(
                new DoorOpenEventHandler(smartHome),
                new DoorClosedEventHandler(smartHome)
        );
        alarm = new Alarm(smartHome, usualHandlers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void alarmSensorEventChecksType() {
        new AlarmSensorEvent(SensorEventType.LIGHT_ON, "objId", "code");
    }

    @Test
    public void activateDeactivateValidNoThrow() {
        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_ACTIVATE, "alarm", "12345"));
        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_DEACTIVATE, "alarm", "12345"));
    }

    @Test
    public void activateDeactivateValidTwiceNoThrow() {
        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_ACTIVATE, "alarm", "12345"));
        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_DEACTIVATE, "alarm", "12345"));

        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_ACTIVATE, "alarm", "other_code"));
        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_DEACTIVATE, "alarm", "other_code"));
    }

    @Test
    public void activateDeactivateInvalidThenTrigger() {
        smartHome.execute(new LightCheck("3", true));
        smartHome.execute(new LightCheck("7", false));

        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_ACTIVATE, "alarm", "12345"));
        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_DEACTIVATE, "alarm", "invalid_code"));

        smartHome.execute(new LightCheck("3", false));
        smartHome.execute(new LightCheck("7", true));
    }

    @Test(expected = IllegalStateException.class)
    public void stateArmedActivateThrows() {
        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_ACTIVATE, "alarm", "12345"));
        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_ACTIVATE, "alarm", "12345"));
    }

    @Test(expected = IllegalStateException.class)
    public void stateStaleThenDeactivateThrows() {
        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_DEACTIVATE, "alarm", "12345"));
    }

    @Test
    public void stateArmedThenTriggerOnEvents() {
        smartHome.execute(new LightCheck("3", true));
        smartHome.execute(new LightCheck("7", false));

        alarm.handleEvent(new AlarmSensorEvent(
                SensorEventType.ALARM_ACTIVATE, "alarm", "12345"));
        alarm.handleEvent(new SensorEvent(
                SensorEventType.DOOR_CLOSED, "3"));

        smartHome.execute(new LightCheck("3", false));
        smartHome.execute(new LightCheck("7", true));
    }

    @Test
    public void stateStaleThenPassToHandlers() {
        smartHome.execute(new DoorCheck("3", true));

        Action action = alarm.handleEvent(new SensorEvent(
                SensorEventType.DOOR_CLOSED, "3"));
        Assert.assertNotNull(action);
        smartHome.execute(action);

        smartHome.execute(new DoorCheck("3", false));
    }
}

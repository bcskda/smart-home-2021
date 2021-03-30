package ru.sbt.mipt.oop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.EventType;
import ru.sbt.mipt.oop.events.handlers.*;

import java.io.InputStream;

public class AlarmTests {
    SmartHome smartHome;
    EventHandler filteredDoorClosedHandler;
    EventHandler alarmEventHandler;

    @Before
    public void setUp() {
        InputStream stream = getClass().getResourceAsStream("test-smart-home.json");
        smartHome = new JsonConfigurationReader(stream).readSmartHome();

        filteredDoorClosedHandler = new FilterByAlarmHandlerDecorator(
                smartHome.getAlarm(), new DoorClosedEventHandler(smartHome)
        );
        alarmEventHandler = new AlarmEventHandler(smartHome.getAlarm());
    }

    @Test
    public void activateDeactivateValidNoThrow() {
        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_ACTIVATE, "12345"));
        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_DEACTIVATE, "12345"));
    }

    @Test
    public void activateDeactivateValidTwiceNoThrow() {
        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_ACTIVATE, "12345"));
        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_DEACTIVATE, "12345"));

        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_ACTIVATE, "other_code"));
        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_DEACTIVATE, "other_code"));
    }

    @Test
    public void activateDeactivateValidNoAction() {
        Action action;

        action = alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_ACTIVATE, "12345"));
        Assert.assertNull(action);

        action = alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_DEACTIVATE, "12345"));
        Assert.assertNull(action);
    }

    @Test
    public void activateDeactivateInvalidThenTrigger() {
        smartHome.execute(new LightCheck("3", true));
        smartHome.execute(new LightCheck("7", false));

        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_ACTIVATE, "12345"));

        Action action = alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_DEACTIVATE, "invalid_code"));
        Assert.assertNotNull(action);
        smartHome.execute(action);

        smartHome.execute(new LightCheck("3", false));
        smartHome.execute(new LightCheck("7", true));
    }

    @Test(expected = IllegalStateException.class)
    public void stateArmedActivateThrows() {
        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_ACTIVATE, "12345"));
        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_ACTIVATE, "12345"));
    }

    @Test(expected = IllegalStateException.class)
    public void stateStaleThenDeactivateThrows() {
        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_DEACTIVATE, "12345"));
    }

    @Test
    public void stateArmedThenTriggerOnEvents() {
        smartHome.execute(new LightCheck("3", true));
        smartHome.execute(new LightCheck("7", false));

        alarmEventHandler.handleEvent(new AlarmEvent(
                EventType.ALARM_ACTIVATE, "12345"));

        Action action = alarmEventHandler.handleEvent(new SensorEvent(
                EventType.DOOR_CLOSED, "3"));
        Assert.assertNotNull(action);
        smartHome.execute(action);

        smartHome.execute(new LightCheck("3", false));
        smartHome.execute(new LightCheck("7", true));
    }

    @Test
    public void stateStaleThenPassToHandlers() {
        Action action;

        smartHome.execute(new DoorCheck("3", true));
        Event doorClosed = new SensorEvent(EventType.DOOR_CLOSED, "3");

        action = alarmEventHandler.handleEvent(doorClosed);
        Assert.assertNull(action);

        action = filteredDoorClosedHandler.handleEvent(doorClosed);
        Assert.assertNotNull(action);
        smartHome.execute(action);

        smartHome.execute(new DoorCheck("3", false));
    }
}

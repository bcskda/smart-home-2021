package ru.sbt.mipt.oop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.handlers.DoorClosedEventHandler;
import ru.sbt.mipt.oop.events.handlers.DoorOpenEventHandler;
import ru.sbt.mipt.oop.events.handlers.HallDoorClosedThenLightsOffHandler;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

import java.io.InputStream;

public class DoorEventTests {
    SmartHome smartHome;

    @Before
    public void setUp() {
        InputStream stream = getClass().getResourceAsStream("test-smart-home.json");
        smartHome = new JsonConfigurationReader(stream).readSmartHome();
    }

    @Test
    public void closeWorks() {
        Assert.assertTrue(smartHome.getDoorById("3").isOpen());
        SensorEventHandler handler = new DoorClosedEventHandler(smartHome);
        smartHome.execute(handler.handleEvent(
                new SensorEvent(SensorEventType.DOOR_CLOSED, "3")));
        Assert.assertFalse(smartHome.getDoorById("3").isOpen());
    }

    @Test
    public void closeIsTargeted() {
        Assert.assertTrue(smartHome.getDoorById("3").isOpen());
        Assert.assertTrue(smartHome.getDoorById("1").isOpen());
        SensorEventHandler handler = new DoorClosedEventHandler(smartHome);
        smartHome.execute(handler.handleEvent(
                new SensorEvent(SensorEventType.DOOR_CLOSED, "3")));
        Assert.assertFalse(smartHome.getDoorById("3").isOpen());
        Assert.assertTrue(smartHome.getDoorById("1").isOpen());
    }

    @Test
    public void closeHallDoor() {
        Assert.fail("Test not implemented");
    }

    @Test
    public void openWorks() {
        Assert.assertFalse(smartHome.getDoorById("2").isOpen());
        SensorEventHandler handler = new DoorOpenEventHandler(smartHome);
        smartHome.execute(handler.handleEvent(
                new SensorEvent(SensorEventType.DOOR_OPEN, "2")));
        Assert.assertTrue(smartHome.getDoorById("2").isOpen());
    }

    @Test
    public void openIsTargeted() {
        Assert.assertFalse(smartHome.getDoorById("2").isOpen());
        Assert.assertFalse(smartHome.getDoorById("4").isOpen());
        SensorEventHandler handler = new DoorOpenEventHandler(smartHome);
        smartHome.execute(handler.handleEvent(
                new SensorEvent(SensorEventType.DOOR_OPEN, "2")));
        Assert.assertTrue(smartHome.getDoorById("2").isOpen());
        Assert.assertFalse(smartHome.getDoorById("4").isOpen());
    }
}

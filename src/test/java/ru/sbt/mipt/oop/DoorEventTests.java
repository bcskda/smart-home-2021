package ru.sbt.mipt.oop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.mipt.oop.commands.handlers.LightOffCommandHandler;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.handlers.DoorClosedEventHandler;
import ru.sbt.mipt.oop.events.handlers.DoorOpenEventHandler;
import ru.sbt.mipt.oop.events.handlers.HallDoorClosedThenLightsOffHandler;
import ru.sbt.mipt.oop.events.handlers.SensorEventHandler;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;

public class DoorEventTests {
    SmartHome smartHome;
    CommandSender controller;

    @Before
    public void setUp() {
        InputStream stream = getClass().getResourceAsStream("test-smart-home.json");
        smartHome = new JsonConfigurationReader(stream).readSmartHome();
        controller = new CommandSenderImpl(smartHome, Collections.singletonList(
                new LightOffCommandHandler(smartHome)
        ));
    }

    @Test
    public void closeWorks() {
        smartHome.execute(new DoorCheck("3", true));
        SensorEventHandler handler = new DoorClosedEventHandler(smartHome);
        smartHome.execute(handler.handleEvent(
                new SensorEvent(SensorEventType.DOOR_CLOSED, "3")));
        smartHome.execute(new DoorCheck("3", false));
    }

    @Test
    public void closeIsTargeted() {
        smartHome.execute(new DoorCheck("3", true));
        smartHome.execute(new DoorCheck("1", true));
        SensorEventHandler handler = new DoorClosedEventHandler(smartHome);
        smartHome.execute(handler.handleEvent(
                new SensorEvent(SensorEventType.DOOR_CLOSED, "3")));
        smartHome.execute(new DoorCheck("3", false));
        smartHome.execute(new DoorCheck("1", true));
    }

    @Test
    public void closeHallDoor() {
        // Close hall door
        SensorEventHandler handler = new HallDoorClosedThenLightsOffHandler(
                controller, smartHome);
        smartHome.execute(handler.handleEvent(
                new SensorEvent(SensorEventType.DOOR_CLOSED, "4")));

        // Check all lights turned off
        smartHome.execute(component -> {
            if (! (component instanceof Light))
                return;
            Light light = (Light) component;
            Assert.assertFalse(light.isOn());
        });
    }

    @Test
    public void openWorks() {
        smartHome.execute(new DoorCheck("2", false));
        SensorEventHandler handler = new DoorOpenEventHandler(smartHome);
        smartHome.execute(handler.handleEvent(
                new SensorEvent(SensorEventType.DOOR_OPEN, "2")));
        smartHome.execute(new DoorCheck("2", true));
    }

    @Test
    public void openIsTargeted() {
        smartHome.execute(new DoorCheck("2", false));
        smartHome.execute(new DoorCheck("4", false));
        SensorEventHandler handler = new DoorOpenEventHandler(smartHome);
        smartHome.execute(handler.handleEvent(
                new SensorEvent(SensorEventType.DOOR_OPEN, "2")));
        smartHome.execute(new DoorCheck("2", true));
        smartHome.execute(new DoorCheck("4", false));
    }
}

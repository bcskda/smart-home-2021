package ru.sbt.mipt.oop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.SensorCommand;
import ru.sbt.mipt.oop.commands.handlers.LightOffCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;

import java.io.InputStream;

public class CommandTests {
    SmartHome smartHome;

    @Before
    public void setUp() {
        InputStream stream = getClass().getResourceAsStream("test-smart-home.json");
        smartHome = new JsonConfigurationReader(stream).readSmartHome();
    }

    @Test
    public void lightOffWorks() {
        Assert.assertTrue(smartHome.getLightById("2").isOn());
        SensorCommandHandler handler = new LightOffCommandHandler(smartHome);
        smartHome.execute(handler.handleCommand(
                new SensorCommand(CommandType.LIGHT_OFF, "2")));
        Assert.assertFalse(smartHome.getLightById("2").isOn());
    }

    @Test
    public void closeIsTargeted() {
        Assert.assertTrue(smartHome.getLightById("2").isOn());
        Assert.assertTrue(smartHome.getLightById("3").isOn());
        SensorCommandHandler handler = new LightOffCommandHandler(smartHome);
        smartHome.execute(handler.handleCommand(
                new SensorCommand(CommandType.LIGHT_OFF, "2")));
        Assert.assertFalse(smartHome.getLightById("2").isOn());
        Assert.assertTrue(smartHome.getLightById("3").isOn());
    }
}

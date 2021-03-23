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
        smartHome.execute(new LightCheck("2", true));
        SensorCommandHandler handler = new LightOffCommandHandler(smartHome);
        smartHome.execute(handler.handleCommand(
                new SensorCommand(CommandType.LIGHT_OFF, "2")));
        smartHome.execute(new LightCheck("2", false));
    }

    @Test
    public void lightOffIsTargeted() {
        smartHome.execute(new LightCheck("2", true));
        smartHome.execute(new LightCheck("3", true));
        SensorCommandHandler handler = new LightOffCommandHandler(smartHome);
        smartHome.execute(handler.handleCommand(
                new SensorCommand(CommandType.LIGHT_OFF, "2")));
        smartHome.execute(new LightCheck("2", false));
        smartHome.execute(new LightCheck("3", true));
    }
}

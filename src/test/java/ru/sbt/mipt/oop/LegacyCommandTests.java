package ru.sbt.mipt.oop;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbt.mipt.oop.commands.legacy.CommandType;
import ru.sbt.mipt.oop.commands.legacy.SensorCommand;
import ru.sbt.mipt.oop.commands.legacy.handlers.LightOffCommandHandler;
import ru.sbt.mipt.oop.commands.legacy.handlers.SensorCommandHandler;

public class LegacyCommandTests {
    SmartHome smartHome;

    @Before
    public void setUp() {
        org.springframework.context.ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        smartHome = context.getBean(SmartHome.class);
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

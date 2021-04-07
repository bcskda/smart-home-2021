package ru.sbt.mipt.oop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbt.mipt.oop.commands.CommandBuilder;
import ru.sbt.mipt.oop.commands.CommandBuilderChain;

public class CommandTests {
    private org.springframework.context.ApplicationContext context;
    private SmartHome smartHome;
    CommandBuilder commandBuilder;

    @Before
    public void setUp() throws Exception {
        context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        smartHome = context.getBean(SmartHome.class);
        commandBuilder = context.getBean(CommandBuilderChain.class).commandBuilder();
    }

    @Test
    public void allRoomsLightOff() {
        commandBuilder.build("allRoomsLightOff").execute();
        smartHome.execute(component -> {
            if (!(component instanceof Light))
                return;
            Light light = (Light) component;
            Assert.assertFalse(light.isOn());
        });
    }

    @Test
    public void allRoomsLightOn() {
        commandBuilder.build("allRoomsLightOn").execute();
        smartHome.execute(component -> {
            if (!(component instanceof Light))
                return;
            Light light = (Light) component;
            Assert.assertTrue(light.isOn());
        });
    }
}

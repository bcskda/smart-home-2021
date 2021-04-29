package ru.sbt.mipt.oop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.commands.CommandBuilder;
import ru.sbt.mipt.oop.commands.CommandBuilderChain;

public class CommandTests {
    private org.springframework.context.ApplicationContext context;
    private SmartHome smartHome;
    private Alarm alarm;
    private CommandBuilder commandBuilder;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        smartHome = context.getBean(SmartHome.class);
        alarm = smartHome.getAlarm();
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

    @Test
    public void corridorLightOn() {
        smartHome.execute(component -> {
            if (!(component instanceof Light))
                return;
            Light light = (Light) component;
            light.setOn(false);
        });
        commandBuilder.build("corridorLightOn").execute();
        smartHome.execute(component -> {
            if (!(component instanceof Room))
                return;
            Room room = (Room) component;
            if (! "corridor".equals(room.getName()))
                return;
            room.forEachLight(light -> {
                Light lightAsLight = (Light) light;
                Assert.assertTrue(lightAsLight.isOn());
            });
        });
    }

    @Test
    public void frontDoorClose() {
        smartHome.execute(component -> {
            if (!(component instanceof Door))
                return;
            Door door = (Door) component;
            if (! "4".equals(door.getId()))
                return;
            door.setOpen(true);
        });
        commandBuilder.build("frontDoorClose").execute();
        smartHome.execute(new DoorCheck("4", false));
    }

    @Test
    public void triggerAlarm() {
        Assert.assertTrue(alarm.isStale());
        commandBuilder.build("triggerAlarm").execute();
        Assert.assertTrue(alarm.isFiring());
    }

    @Test
    public void activateAlarm() {
        Assert.assertTrue(alarm.isStale());
        commandBuilder.build("activateAlarm").execute();
        Assert.assertTrue(alarm.isArmed());
    }

    @Test
    public void activateAlarmDefaultCode() {
        activateAlarm();
        alarm.deactivate("");
        Assert.assertTrue(alarm.isStale());
    }
}

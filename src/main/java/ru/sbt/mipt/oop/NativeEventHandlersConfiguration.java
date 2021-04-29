package ru.sbt.mipt.oop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.actions.PairActionDecorator;
import ru.sbt.mipt.oop.actions.RunOnceDecorator;
import ru.sbt.mipt.oop.actions.ToggleLights;
import ru.sbt.mipt.oop.events.handlers.*;

@Configuration
public class NativeEventHandlersConfiguration {
    @Autowired
    private SmartHome smartHome;

    @Autowired
    private CommandSender commandSender;

    @Qualifier("noWrap")
    @Bean
    public EventHandler alarmSecurityEventHandler() {
        AlarmSecurityEventHandler handler = new AlarmSecurityEventHandler(
                smartHome.getAlarm(),
                new AlarmStateUpdateHandler(smartHome.getAlarm()));
        return handler
                .setOnAlarmArmed(onSensorWhenArmedHandler())
                .setOnAlarmFiring(alwaysWhenFiringHandler());
    }

    @Bean
    public EventHandler alwaysWhenFiringHandler() {
        return new WithNotifyHandlerDecorator(
                smartHome.getNotificationSender(),
                new UnconditionalHandler(new PairActionDecorator(
                        new RunOnceDecorator(() -> smartHome.getAlarm().trigger()),
                        new ToggleLights()
                ))
        );
    }

    @Bean
    public EventHandler onSensorWhenArmedHandler() {
        return new FilterOnSensorHandlerDecorator(alwaysWhenFiringHandler());
    }

    @Qualifier("sensor")
    @Bean
    public EventHandler lightOnEventHandler() {
        return new LightOnEventHandler(smartHome);
    }

    @Qualifier("sensor")
    @Bean
    public EventHandler lightOffEventHandler() {
        return new LightOffEventHandler(smartHome);
    }

    @Qualifier("sensor")
    @Bean
    public EventHandler doorOpenEventHandler() {
        return new DoorOpenEventHandler(smartHome);
    }

    @Qualifier("sensor")
    @Bean
    public EventHandler doorClosedEventHandler() {
        return new DoorClosedEventHandler(smartHome);
    }

    @Qualifier("sensor")
    @Bean
    public EventHandler hallDoorClosedThenLightsOff() {
        return new HallDoorClosedThenLightsOffHandler(commandSender, smartHome);
    }
}

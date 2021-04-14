package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.actions.PairActionDecorator;
import ru.sbt.mipt.oop.actions.RunOnceDecorator;
import ru.sbt.mipt.oop.actions.ToggleLights;
import ru.sbt.mipt.oop.ccadapt.EventHandlerAdaptor;
import ru.sbt.mipt.oop.commands.handlers.LightOffCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.LogCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;
import ru.sbt.mipt.oop.events.handlers.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfiguration {
    @Bean
    SmartHome smartHome() {
        // считываем состояние дома из файла
        String filename = "smart-home-1.json";
        try {
            return new JsonConfigurationReader(Paths.get(filename)).readSmartHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    CommandSender commandSender() {
        SmartHome smartHome = smartHome();
        List<SensorCommandHandler> commandHandlers = Arrays.asList(
                new LogCommandHandler(),
                new LightOffCommandHandler(smartHome)
        );
        return new CommandSenderImpl(smartHome(), commandHandlers);
    }

    @Bean
    SensorEventsManager sensorEventsManager() {
        SensorEventsManager eventsManager = new SensorEventsManager();
        ccAdaptedEventHandlers().forEach(eventsManager::registerEventHandler);
        return eventsManager;
    }

    @Bean
    List<com.coolcompany.smarthome.events.EventHandler> ccAdaptedEventHandlers() {
        SmartHome smartHome = smartHome();
        return eventHandlers().stream().map(handler -> new EventHandlerAdaptor(handler, smartHome))
                .collect(Collectors.toList());
    }

    @Bean
    List<EventHandler> eventHandlers() {
        SmartHome smartHome = smartHome();

        List<EventHandler> wrappedSensorHandlers = usualSensorEventHandlers().stream().map(
                eventHandler -> new FilterByAlarmHandlerDecorator(smartHome.getAlarm(), eventHandler)
        ).collect(Collectors.toList());

        List<EventHandler> allHandlers = new ArrayList<>();
        allHandlers.add(new LogEventHandler());
        allHandlers.add(alarmSecurityEventHandler());
        allHandlers.addAll(wrappedSensorHandlers);

        return allHandlers;
    }

    @Bean
    EventHandler alarmSecurityEventHandler() {
        AlarmSecurityEventHandler handler = new AlarmSecurityEventHandler(
                smartHome().getAlarm(),
                new AlarmStateUpdateHandler(smartHome().getAlarm()));
        return handler
                .setOnAlarmArmed(onSensorWhenArmedHandler())
                .setOnAlarmFiring(alwaysWhenFiringHandler());
    }

    @Bean
    EventHandler alwaysWhenFiringHandler() {
        return new WithNotifyHandlerDecorator(
                smartHome().getNotificationSender(),
                new UnconditionalHandler(new PairActionDecorator(
                        new RunOnceDecorator(() -> smartHome().getAlarm().trigger()),
                        new ToggleLights()
                ))
        );
    }

    @Bean
    EventHandler onSensorWhenArmedHandler() {
        return new FilterOnSensorHandlerDecorator(alwaysWhenFiringHandler());
    }

    @Bean
    List<EventHandler> usualSensorEventHandlers() {
        SmartHome smartHome = smartHome();
        CommandSender commandSender = commandSender();

        return Arrays.asList(
                new LightOnEventHandler(smartHome),
                new LightOffEventHandler(smartHome),
                new DoorOpenEventHandler(smartHome),
                new DoorClosedEventHandler(smartHome),
                new HallDoorClosedThenLightsOffHandler(commandSender, smartHome));
    }
}

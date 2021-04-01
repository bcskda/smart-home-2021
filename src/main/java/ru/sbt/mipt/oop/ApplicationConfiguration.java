package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.actions.PairActionDecorator;
import ru.sbt.mipt.oop.actions.RunOnceDecorator;
import ru.sbt.mipt.oop.actions.ToggleLights;
import ru.sbt.mipt.oop.ccadapt.EventHandlerAdaptorFactory;
import ru.sbt.mipt.oop.commands.handlers.LightOffCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.LogCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;
import ru.sbt.mipt.oop.events.handlers.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Configuration
public class ApplicationConfiguration {
    @Bean
    SmartHome getSmartHome() {
        // считываем состояние дома из файла
        String filename = "smart-home-1.json";
        try {
            return new JsonConfigurationReader(Paths.get(filename)).readSmartHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    CommandSender getCommandSender() {
        SmartHome smartHome = getSmartHome();
        List<SensorCommandHandler> commandHandlers = Arrays.asList(
                new LogCommandHandler(),
                new LightOffCommandHandler(smartHome)
        );
        return new CommandSenderImpl(getSmartHome(), commandHandlers);
    }

    @Bean
    SensorEventsManager getSensorEventsManager() {
        SensorEventsManager eventsManager = new SensorEventsManager();
        Stream<com.coolcompany.smarthome.events.EventHandler> adaptedEventHandlers = getAdaptedEventHandlers();
        adaptedEventHandlers.forEach(eventsManager::registerEventHandler);
        return eventsManager;
    }

    Stream<com.coolcompany.smarthome.events.EventHandler> getAdaptedEventHandlers() {
        SmartHome smartHome = getSmartHome();
        EventHandlerAdaptorFactory eventHandlerAdaptorFactory = new EventHandlerAdaptorFactory(action -> {
            if (action != null)
                smartHome.execute(action);
        });
        Collection<EventHandler> eventHandlers = getEventHandlers();
        return eventHandlers.stream().map(eventHandlerAdaptorFactory::adapt);
    }

    List<EventHandler> getEventHandlers() {
        SmartHome smartHome = getSmartHome();

        Stream<EventHandler> wrappedSensorHandlers = getUsualEventHandlers().stream().map(
                eventHandler -> new FilterByAlarmHandlerDecorator(smartHome.getAlarm(), eventHandler)
        );

        EventHandler alwaysWhenFiring = new WithNotifyHandlerDecorator(
                smartHome.getNotificationSender(),
                new UnconditionalHandler(new PairActionDecorator(
                        new RunOnceDecorator(() -> smartHome.getAlarm().trigger()),
                        new ToggleLights()
                ))
        );
        EventHandler onSensorWhenArmed = new FilterOnSensorHandlerDecorator(alwaysWhenFiring);

        List<EventHandler> allHandlers = new ArrayList<>();
        allHandlers.add(new LogEventHandler());
        allHandlers.add(
                new AlarmSecurityEventHandler(
                        smartHome.getAlarm(),
                        new AlarmStateUpdateHandler(smartHome.getAlarm()))
                        .setOnAlarmArmed(onSensorWhenArmed)
                        .setOnAlarmFiring(alwaysWhenFiring)
        );
        wrappedSensorHandlers.forEach(allHandlers::add);

        return allHandlers;
    }

    List<EventHandler> getUsualEventHandlers() {
        SmartHome smartHome = getSmartHome();
        CommandSender commandSender = getCommandSender();

        return Arrays.asList(
                new LightOnEventHandler(smartHome),
                new LightOffEventHandler(smartHome),
                new DoorOpenEventHandler(smartHome),
                new DoorClosedEventHandler(smartHome),
                new HallDoorClosedThenLightsOffHandler(commandSender, smartHome));
    }
}

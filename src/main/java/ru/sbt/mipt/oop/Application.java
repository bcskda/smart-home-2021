package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.actions.PairActionDecorator;
import ru.sbt.mipt.oop.actions.RunOnceDecorator;
import ru.sbt.mipt.oop.actions.ToggleLights;
import ru.sbt.mipt.oop.ccadapt.EventHandlerAdaptorFactory;
import ru.sbt.mipt.oop.commands.handlers.LightOffCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.LogCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;
import ru.sbt.mipt.oop.events.handlers.*;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Application {
    public static String DEFAULT_CONF_PATH = "smart-home-1.json";

    public static void main(String[] args) {
        com.coolcompany.smarthome.events.SensorEventsManager eventsManager = new com.coolcompany.smarthome.events.SensorEventsManager();
        try {
            // считываем состояние дома из файла
            String filename = (args.length > 0) ? args[0] : DEFAULT_CONF_PATH;
            SmartHome smartHome = new JsonConfigurationReader(Paths.get(filename)).readSmartHome();

            // обработчики и контроллер команд
            CommandSender commandSender = new CommandSenderImpl(smartHome, makeCommandHandlers(smartHome));

            // обработчики событий
            EventHandlerAdaptorFactory eventHandlerAdaptorFactory = new EventHandlerAdaptorFactory(action -> {
                if (action != null)
                    smartHome.execute(action);
            });
            Collection<EventHandler> eventHandlers = makeEventHandlers(smartHome, commandSender);
            eventHandlers.stream().map(eventHandlerAdaptorFactory::adapt).forEach(eventsManager::registerEventHandler);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to configure Application", e);
        }
        eventsManager.start();
    }

    private static List<SensorCommandHandler> makeCommandHandlers(SmartHome smartHome) {
        List<SensorCommandHandler> handlers = new ArrayList<>();
        handlers.add(new LogCommandHandler());
        handlers.add(new LightOffCommandHandler(smartHome));
        return handlers;
    }

    private static List<EventHandler> makeEventHandlers(SmartHome smartHome, CommandSender commandSender) {
        List<EventHandler> sensorHandlers = Arrays.asList(
                new LightOnEventHandler(smartHome),
                new LightOffEventHandler(smartHome),
                new DoorOpenEventHandler(smartHome),
                new DoorClosedEventHandler(smartHome),
                new HallDoorClosedThenLightsOffHandler(commandSender, smartHome));
        Stream<EventHandler> wrappedSensorHandlers = sensorHandlers.stream().map(
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
}

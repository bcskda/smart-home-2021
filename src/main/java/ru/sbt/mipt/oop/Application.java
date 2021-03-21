package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.alarm.Alarm;
import ru.sbt.mipt.oop.commands.handlers.LightOffCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.LogCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;
import ru.sbt.mipt.oop.events.handlers.*;
import ru.sbt.mipt.oop.events.sources.RandomSensorEventSource;

import java.nio.file.Paths;
import java.util.*;

public class Application {
    public static String DEFAULT_CONF_PATH = "smart-home-1.json";

    public static void main(String[] args) {
        SensorEventLoop eventLoop;
        try {
            // считываем состояние дома из файла
            String filename = (args.length > 0) ? args[0] : DEFAULT_CONF_PATH;
            SmartHome smartHome = new JsonConfigurationReader(Paths.get(filename)).readSmartHome();

            // обработчики и контроллер команд
            CommandSender commandSender = new CommandSenderImpl(smartHome, makeCommandHandlers(smartHome));

            // обработчики событий
            eventLoop = new SensorEventLoop(
                    smartHome,
                    new RandomSensorEventSource(),
                    makeEventHandlers(smartHome, commandSender));
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to configure Application", e);
        }
        eventLoop.runCatchSuppress();
    }

    private static List<SensorCommandHandler> makeCommandHandlers(SmartHome smartHome) {
        List<SensorCommandHandler> handlers = new ArrayList<>();
        handlers.add(new LogCommandHandler());
        handlers.add(new LightOffCommandHandler(smartHome));
        return handlers;
    }

    private static List<SensorEventHandler> makeEventHandlers(SmartHome smartHome, CommandSender commandSender) {
        List<SensorEventHandler> handlers = Arrays.asList(
                new LogEventHandler(),
                new LightOnEventHandler(smartHome),
                new LightOffEventHandler(smartHome),
                new DoorOpenEventHandler(smartHome),
                new DoorClosedEventHandler(smartHome),
                new HallDoorClosedThenLightsOffHandler(commandSender, smartHome));
        return Collections.singletonList(
                new Alarm(smartHome, handlers));
    }
}

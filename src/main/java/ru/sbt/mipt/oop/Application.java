package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.CommandType;
import ru.sbt.mipt.oop.commands.handlers.LightOffCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.LogCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;
import ru.sbt.mipt.oop.events.handlers.*;
import ru.sbt.mipt.oop.events.sources.SensorEventSource;
import ru.sbt.mipt.oop.events.sources.SensorEventSourceStub;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class Application {
    public static String DEFAULT_CONF_PATH = "smart-home-1.json";

    private final SmartHome smartHome;
    private final SmartHomeController smartHomeController;
    private final SensorEventLoop eventLoop;

    public static void main(String[] args) {
        Application app;
        try {
            app = new Application(args);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to configure Application", e);
        }
        app.run();
    }

    Application(String[] args) throws IOException {
        // считываем состояние дома из файла
        String filename = (args.length > 0) ? args[0] : DEFAULT_CONF_PATH;
        InputStream smartHomeStream = Files.newInputStream(
                Paths.get(filename), StandardOpenOption.READ);
        smartHome = new JsonConfigurationReader().readSmartHome(smartHomeStream);

        // обработчики команд и контроллер
        Map<CommandType, SensorCommandHandler> commandHandlers = initCommandHandlers(smartHome);
        smartHomeController = new SmartHomeControllerImpl(smartHome, commandHandlers, new LogCommandHandler());

        // создаём обработчики событий
        SensorEventSource eventSource = new SensorEventSourceStub();
        List<SensorEventHandler> eventHandlers = initEventHandlers();
        eventLoop = new SensorEventLoop(eventSource, eventHandlers);
    }

    public void run() {
        eventLoop.runCatchSuppress();
    }

    private Map<CommandType, SensorCommandHandler> initCommandHandlers(SmartHome smartHome) {
        Map<CommandType, SensorCommandHandler> handlers = new HashMap<>();
        Map<String, Light> lightsById = new HashMap<>();
        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                lightsById.put(light.getId(), light);
            }
        }
        handlers.put(CommandType.LIGHT_OFF, new LightOffCommandHandler(lightsById));
        return handlers;
    }

    private List<SensorEventHandler> initEventHandlers() {
        List<SensorEventHandler> handlers = new ArrayList<>();
        handlers.add(new LogEventHandler());
        initLightHandlers(handlers);
        initDoorHandlers(handlers);
        return handlers;
    }

    private void initLightHandlers(List<SensorEventHandler> handlers) {
        handlers.add(new LightOnEventHandler(smartHome));
        handlers.add(new LightOffEventHandler(smartHome));
    }

    private void initDoorHandlers(List<SensorEventHandler> handlers) {
        handlers.add(new DoorOpenEventHandler(smartHome));
        handlers.add(new DoorClosedEventHandler(smartHome));
        handlers.add(new HallDoorClosedThenLightsOffHandler(
                smartHomeController, smartHome));
    }
}

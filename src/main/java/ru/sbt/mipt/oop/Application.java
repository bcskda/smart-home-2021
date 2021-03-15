package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.commands.handlers.LightOffCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.LogCommandHandler;
import ru.sbt.mipt.oop.commands.handlers.SensorCommandHandler;
import ru.sbt.mipt.oop.events.handlers.*;
import ru.sbt.mipt.oop.events.sources.SensorEventSource;
import ru.sbt.mipt.oop.events.sources.SensorEventSourceStub;

import java.io.IOException;
import java.nio.file.Paths;
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
        smartHome = new JsonConfigurationReader(Paths.get(filename)).readSmartHome();

        // обработчики команд и контроллер
        List<SensorCommandHandler> commandHandlers = initCommandHandlers();
        smartHomeController = new SmartHomeControllerImpl(smartHome, commandHandlers);

        // создаём обработчики событий
        SensorEventSource eventSource = new SensorEventSourceStub();
        List<SensorEventHandler> eventHandlers = initEventHandlers();
        eventLoop = new SensorEventLoop(eventSource, eventHandlers);
    }

    public void run() {
        eventLoop.runCatchSuppress();
    }

    private List<SensorCommandHandler> initCommandHandlers() {
        List<SensorCommandHandler> handlers = new ArrayList<>();
        handlers.add(new LogCommandHandler());
        handlers.add(new LightOffCommandHandler(smartHome));
        return handlers;
    }

    private List<SensorEventHandler> initEventHandlers() {
        List<SensorEventHandler> handlers = new ArrayList<>();
        handlers.add(new LogEventHandler());

        handlers.add(new LightOnEventHandler(smartHome));
        handlers.add(new LightOffEventHandler(smartHome));

        handlers.add(new DoorOpenEventHandler(smartHome));
        handlers.add(new DoorClosedEventHandler(smartHome));
        handlers.add(new HallDoorClosedThenLightsOffHandler(smartHomeController, smartHome));
        return handlers;
    }

}

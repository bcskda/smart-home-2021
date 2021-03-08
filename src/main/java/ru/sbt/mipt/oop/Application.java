package ru.sbt.mipt.oop;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import static ru.sbt.mipt.oop.SensorEventType.*;

public class Application {
    public static String DEFAULT_CONF_PATH = "smart-home-1.json";

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
        SmartHome smartHome = new JsonConfigurationReader().readSmartHome(smartHomeStream);

        // заглушка контроллера - логгирует команды
        smartHomeController = new SmartHomeControllerStub(smartHome);

        // создаём обработчики событий
        SensorEventSource eventSource = new SensorEventSourceStub();
        Map<SensorEventType, SensorEventHandler> eventHandlers = initEventHandlers();
        eventLoop = new SensorEventLoop(eventSource, eventHandlers, new LogEventHandler());
    }

    public void run() {
        eventLoop.runCatchSuppress();
    }

    private Map<SensorEventType, SensorEventHandler> initEventHandlers() {
        Map<SensorEventType, SensorEventHandler> handlers = new HashMap<>();
        initLightHandlers(handlers);
        initDoorHandlers(handlers);
        return handlers;
    }

    private void initLightHandlers(Map<SensorEventType, SensorEventHandler> handlers) {
        Map<String, Light> lightsById = new HashMap<>();
        for (Room room : smartHomeController.getHome().getRooms()) {
            for (Light light : room.getLights()) {
                lightsById.put(light.getId(), light);
            }
        }
        handlers.put(LIGHT_ON, new GlobalLightOnEventHandler(lightsById));
        handlers.put(LIGHT_OFF, new GlobalLightOffEventHandler(lightsById));
    }

    private void initDoorHandlers(Map<SensorEventType, SensorEventHandler> handlers) {
        Map<String, Door> doorsById = new HashMap<>();
        for (Room room : smartHomeController.getHome().getRooms()) {
            for (Door door : room.getDoors()) {
                doorsById.put(door.getId(), door);
            }
        }
        handlers.put(DOOR_OPEN, new GlobalDoorOpenEventHandler(doorsById));
        handlers.put(DOOR_CLOSED, new GlobalDoorClosedEventHandler(
                smartHomeController, doorsById));
    }
}

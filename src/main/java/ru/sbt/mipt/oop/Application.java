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
    private final SmartHomeController smartHomeController;
    SensorEventLoop eventLoop;

    public static void main(String... args) throws IOException {
        Application app = new Application(args);
        app.run();
    }

    Application(String... args) throws IOException {
        // считываем состояние дома из файла
        InputStream smartHomeStream = Files.newInputStream(
                Paths.get("smart-home-1.json"), StandardOpenOption.READ);
        SmartHome smartHome = new JsonConfigurationReader().readSmartHome(smartHomeStream);
        smartHomeController = new SmartHomeControllerStub(smartHome);

        // создаём обработчики событий
        SensorEventSource eventSource = new SensorEventSourceStub();
        Map<SensorEventType, SensorEventHandler> eventHandlers = initEventHandlers();
        eventLoop = new SensorEventLoop(eventSource, eventHandlers, new LogEventHandler());
    }

    public void run() {
        // начинаем цикл обработки событий
        boolean hasEvents = true;
        while (hasEvents) {
            try {
                hasEvents = eventLoop.runOnce();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

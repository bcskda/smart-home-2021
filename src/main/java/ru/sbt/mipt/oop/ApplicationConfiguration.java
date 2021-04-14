package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.ccadapt.EventHandlerAdaptor;
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
import java.util.stream.Collectors;

@Configuration
@ComponentScan
public class ApplicationConfiguration {
    @Bean
    public SmartHome smartHome() {
        // считываем состояние дома из файла
        String filename = "smart-home-1.json";
        try {
            return new JsonConfigurationReader(Paths.get(filename)).readSmartHome();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public CommandSender commandSender() {
        SmartHome smartHome = smartHome();
        List<SensorCommandHandler> commandHandlers = Arrays.asList(
                new LogCommandHandler(),
                new LightOffCommandHandler(smartHome)
        );
        return new CommandSenderImpl(smartHome(), commandHandlers);
    }

    @Bean
    public EventHandler logEventHandler() {
        return new LogEventHandler();
    }

    @Bean
    public SensorEventsManager sensorEventsManager(@Qualifier("sensor") Collection<EventHandler> sensorEventHandlers,
                                            @Qualifier("alarm") EventHandler alarmEventHandler) {
        SmartHome smartHome = smartHome();

        List<EventHandler> wrappedSensorEventHandlers = alarmWrapSensorEventHandlers(sensorEventHandlers);
        List<EventHandler> allHandlers = getAllNativeHandlers(alarmEventHandler, wrappedSensorEventHandlers);
        List<com.coolcompany.smarthome.events.EventHandler> adaptedHandlers = ccAdaptHandlers(allHandlers);

        SensorEventsManager eventsManager = new SensorEventsManager();
        adaptedHandlers.forEach(eventsManager::registerEventHandler);
        return eventsManager;
    }


    private List<EventHandler> alarmWrapSensorEventHandlers(Collection<EventHandler> sensorEventHandlers) {
        return sensorEventHandlers.stream().map(
                eventHandler -> new FilterByAlarmHandlerDecorator(smartHome().getAlarm(), eventHandler)
        ).collect(Collectors.toList());
    }

    private List<EventHandler> getAllNativeHandlers(EventHandler alarmEventHandler,
                                                    List<EventHandler> wrappedSensorEventHandlers) {
        List<EventHandler> allHandlers = new ArrayList<>();
        allHandlers.add(logEventHandler());
        allHandlers.add(alarmEventHandler);
        allHandlers.addAll(wrappedSensorEventHandlers);
        return allHandlers;
    }

    private List<com.coolcompany.smarthome.events.EventHandler> ccAdaptHandlers(List<EventHandler> nativeHandlers) {
        return nativeHandlers.stream().map(handler -> new EventHandlerAdaptor(handler, smartHome()))
                .collect(Collectors.toList());
    }
}

package ru.sbt.mipt.oop.events.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.actions.AllDecorator;
import ru.sbt.mipt.oop.events.Event;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ForEachSensorEventHandler implements EventHandler {
    @Autowired private SensorEventHandler[] sensorEventHandlers;

    @Override
    public Action handleEvent(Event event) {
        List<Action> toExecute = Arrays.stream(sensorEventHandlers).map(handler -> handler.handleEvent(event))
                .collect(Collectors.toList());
        return new AllDecorator(toExecute);
    }
}

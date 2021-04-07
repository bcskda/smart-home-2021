package ru.sbt.mipt.oop.events.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.EventType.DOOR_CLOSED;

@Component
public class HallDoorClosedThenLightsOffHandler implements SensorEventHandler {
    @Autowired private SmartHome smartHome;

    @Bean
    public HallDoorClosedThenLightsOffHandler hallDoorClosedThenLightsOffHandler() {
        return this;
    }

    @Override
    public Action handleEvent(Event event) {
        if (event.getType() != DOOR_CLOSED)
            return null;
        return onAnyDoorClosed((SensorEvent) event);
    }

    private Action onAnyDoorClosed(SensorEvent event) {
        return component -> {
            if (! (component instanceof Room))
                return;
            Room room = (Room) component;
            if (! "hall".equals(room.getName()))
                return;
            room.execute(containsThisDoorThen(event, () -> smartHome.execute(onHallDoorClose())));
        };
    }

    private Action containsThisDoorThen(SensorEvent event, Runnable then) {
        return component -> {
            if (! (component instanceof Door))
                return;
            Door door = ((Door) component);
            if (! event.getObjectId().equals(door.getId()))
                return;
            then.run();
        };
    }

    private Action onHallDoorClose() {
        return component -> {
            if (! (component instanceof Room))
                return;
            Room asRoom = (Room) component;
            asRoom.forEachLight(light -> ((Light) light).setOn(false));
        };
    }
}

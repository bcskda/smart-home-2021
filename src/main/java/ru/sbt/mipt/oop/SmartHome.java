package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.alarm.Alarm;

import java.util.ArrayList;
import java.util.Collection;

public class SmartHome implements Actionable {
    final private Collection<Room> rooms;
    final private Alarm alarm;
    final private NotificationSender notificationSender;

    public SmartHome() {
        this(new ArrayList<>());
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
        this.alarm = new Alarm();
        this.notificationSender = new NotificationSender();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public NotificationSender getNotificationSender() {
        return notificationSender;
    }

    @Override
    public void execute(Action action) {
        action.execute(this);
        forEachRoom(action);
    }

    private void forEachRoom(Action action) {
        rooms.forEach(room -> room.execute(action));
    }
}

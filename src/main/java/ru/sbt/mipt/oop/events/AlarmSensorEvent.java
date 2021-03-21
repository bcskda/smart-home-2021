package ru.sbt.mipt.oop.events;

public class AlarmSensorEvent extends SensorEvent {
    String code;

    public AlarmSensorEvent(SensorEventType type, String objectId, String code) {
        super(type, objectId);
        this.code = code;
    }
}

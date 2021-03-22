package ru.sbt.mipt.oop.events;

public class AlarmSensorEvent extends SensorEvent {
    String code;

    public AlarmSensorEvent(SensorEventType type, String objectId, String code) {
        super(type, objectId);
        switch (type) {
            case ALARM_ACTIVATE:
            case ALARM_DEACTIVATE:
                break;
            default:
                throw new IllegalArgumentException(
                        "Invalid event type for AlarmSensorEvent: " + type);
        }
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

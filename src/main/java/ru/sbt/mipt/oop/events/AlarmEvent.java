package ru.sbt.mipt.oop.events;

public class AlarmEvent implements Event {
    private final EventType type;
    private final String code;

    public AlarmEvent(EventType type, String code) {
        if (!isValidEventType(type)) {
            throw new IllegalArgumentException(
                    "Invalid event type for AlarmSensorEvent: " + type);
        }
        this.type = type;
        this.code = code;
    }

    @Override
    public EventType getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    private static boolean isValidEventType(EventType type) {
        switch (type) {
            case ALARM_ACTIVATE:
            case ALARM_DEACTIVATE:
                return true;
            default:
                return false;
        }
    }
}

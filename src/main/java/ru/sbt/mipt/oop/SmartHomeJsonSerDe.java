package ru.sbt.mipt.oop;

import com.google.gson.*;

import java.lang.reflect.Type;

public class SmartHomeJsonSerDe implements JsonSerializer<SmartHome>, JsonDeserializer<SmartHome> {
    @Override
    public JsonElement serialize(SmartHome smartHome, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(smartHome.storage);
    }

    @Override
    public SmartHome deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        SmartHomeStorage storage = context.deserialize(jsonElement, SmartHomeStorage.class);
        return new SmartHome(storage.rooms);
    }
}

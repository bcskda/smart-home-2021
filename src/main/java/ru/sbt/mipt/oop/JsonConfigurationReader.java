package ru.sbt.mipt.oop;

import com.google.gson.Gson;

import java.io.*;
import java.util.Collection;
import java.util.stream.Collectors;

public class JsonConfigurationReader implements ConfigurationReader {
    static Gson gson = new Gson();
    @Override
    public SmartHome readSmartHome(InputStream stream) {
        Reader reader = new BufferedReader(new InputStreamReader(stream));
        SmartHome smartHome = gson.fromJson(reader, SmartHome.class);
        fillParentReferences(smartHome);
        return smartHome;
    }

    void fillParentReferences(SmartHome smartHome) {
        fillLightsRoom(smartHome);
        fillDoorsRoom(smartHome);
    }

    void fillLightsRoom(SmartHome smartHome) {
        for (Room room : smartHome.getRooms()) {
            // immutable lights
            Collection<Light> lightsWithParent = room.getLights().stream().map(
                    light -> new Light(light.getId(), room, light.isOn())
            ).collect(Collectors.toList());
            room.getLights().clear();
            room.getLights().addAll(lightsWithParent);
        }
    }

    void fillDoorsRoom(SmartHome smartHome) {
        for (Room room : smartHome.getRooms()) {
            Collection<Door> doorsWithParent = room.getDoors().stream().map(
                    door -> new Door(door.getId(), room, door.isOpen())
            ).collect(Collectors.toList());
            room.getDoors().clear();
            room.getDoors().addAll(doorsWithParent);
        }
    }
}

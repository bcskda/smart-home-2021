package ru.sbt.mipt.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.stream.Collectors;

public class JsonConfigurationReader implements ConfigurationReader {
    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(SmartHome.class, new SmartHomeJsonSerDe())
            .create();
    InputStream stream;

    public JsonConfigurationReader(InputStream stream) {
        this.stream = stream;
    }

    public JsonConfigurationReader(Path configPath) throws IOException {
        this(Files.newInputStream(configPath, StandardOpenOption.READ));
    }

    @Override
    public SmartHome readSmartHome() {
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

package ru.sbt.mipt.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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
        return gson.fromJson(reader, SmartHome.class);
    }
}

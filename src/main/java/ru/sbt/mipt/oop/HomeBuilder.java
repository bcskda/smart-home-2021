package ru.sbt.mipt.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HomeBuilder {

    public static void main(String[] args) throws IOException {
        Room kitchen = new Room(new ArrayList<>(), new ArrayList<>(), "kitchen");
        kitchen.getLights().addAll(Arrays.asList(
                new Light("1", false),
                new Light("2", true)));
        kitchen.getDoors().addAll(Collections.singletonList(
                new Door("1", false)));

        Room bathroom = new Room(new ArrayList<>(), new ArrayList<>(), "bathroom");
        bathroom.getLights().addAll(Collections.singletonList(
                new Light("3", true)));
        bathroom.getDoors().addAll(Collections.singletonList(
                new Door("2", false)));

        Room bedroom = new Room(new ArrayList<>(), new ArrayList<>(), "bedroom");
        bedroom.getLights().addAll(Arrays.asList(
                new Light("4", false),
                new Light("5", false),
                new Light("6", false)));
        bedroom.getDoors().addAll(Collections.singletonList(
                new Door("3",  true)));

        Room hall = new Room(new ArrayList<>(), new ArrayList<>(), "hall");
        hall.getLights().addAll(Arrays.asList(
                new Light("7", false),
                new Light("8", false),
                new Light("9", false)));
        hall.getDoors().addAll(Collections.singletonList(
                new Door("4", false)));

        SmartHome smartHome = new SmartHome(Arrays.asList(kitchen, bathroom, bedroom, hall));
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SmartHome.class, new SmartHomeJsonSerDe())
                .excludeFieldsWithoutExposeAnnotation().setPrettyPrinting()
                .create();
        String jsonString = gson.toJson(smartHome);
        System.out.println(jsonString);
        Path path = Paths.get("output.json");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(jsonString);
        }
    }

}

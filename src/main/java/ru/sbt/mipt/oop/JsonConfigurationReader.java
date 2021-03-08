package ru.sbt.mipt.oop;

import com.google.gson.Gson;

import java.io.*;

public class JsonConfigurationReader implements ConfigurationReader {
    static Gson gson = new Gson();
    @Override
    public SmartHome readSmartHome(InputStream stream) throws IOException {
        Reader reader = new BufferedReader(new InputStreamReader(stream));
        SmartHome smartHome = gson.fromJson(reader, SmartHome.class);
        // TODO fill parent references
        return smartHome;
    }
}

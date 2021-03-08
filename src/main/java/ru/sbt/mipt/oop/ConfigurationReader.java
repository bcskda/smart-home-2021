package ru.sbt.mipt.oop;

import java.io.IOException;
import java.io.InputStream;

public interface ConfigurationReader {
    SmartHome readSmartHome(InputStream stream) throws IOException;
}

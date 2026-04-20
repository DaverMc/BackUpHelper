package de.daver.backup.util;

import de.daver.backup.LoggingHelper;
import de.daver.backup.function.Deserializer;
import de.daver.backup.function.Serializer;
import de.daver.backup.io.FileHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class Properties {

    private final Map<String, String> properties;

    public Properties() {
        this.properties = new ConcurrentHashMap<>();
    }

    public void load(Path file) {
        try {
            Files.readAllLines(file).forEach(this::checkLine);
        } catch (IOException exception) {
            LoggingHelper.error(exception);
        }
    }

    public void putString(String key, String value) {
        properties.put(key, value);
    }

    public <T> void put(String key, T value, Serializer<T> serializer) {
        putString(key, serializer.serialize(value));
    }

    public void put(String key, Object value) {
        putString(key, String.valueOf(value));
    }

    private void checkLine(String line) {
        if(line.isBlank() || line.startsWith("#")) return;
        var parts = line.split("=", 2);
        if(parts.length < 2) return;
        var key = parts[0];
        var value = line.substring(key.length() + 1);
        properties.put(key.trim(), value.trim());
    }

    public String getString(String key) {
        return properties.get(key);
    }

    public <T> T get(String key, Deserializer<T> deserializer) {
        var string = getString(key);
        if(string == null) return null;
        return deserializer.deserialize(string);
    }

    public <E extends Enum<E>> E getEnumValue(String key, Class<E> enumClass) {
        var string = getString(key);
        if(string == null) return null;
        return Enum.valueOf(enumClass, string);
    }
}

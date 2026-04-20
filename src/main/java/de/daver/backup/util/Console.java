package de.daver.backup.util;

import de.daver.backup.function.Deserializer;

public class Console {

    private Console() {
        throw new RuntimeException("This is a static class!");
    }

    public static  <T> T readInput(String prompt, Deserializer<T> deserializer) {
        return deserializer.deserialize(readString(prompt));
    }

    public static String readString(String prompt) {
        return IO.readln(prompt);
    }

    public static int readInt(String prompt) {
        return readInput(prompt, Integer::parseInt);
    }

}

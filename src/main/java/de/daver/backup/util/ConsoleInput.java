package de.daver.backup.util;

import de.daver.backup.function.Deserializer;

public class ConsoleInput {

    public static <T> T readInput(String prompt, Deserializer<T> deserializer) {
        return deserializer.deserialize(IO.readln(prompt));
    }

}

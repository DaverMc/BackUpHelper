package de.daver.backup.function;

public interface Deserializer<T> {

    T deserialize(String string);

}

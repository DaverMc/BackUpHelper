package de.daver.backup.function;

public interface Serializer<T> {

    String serialize(T value);

}

package de.daver.backup.program;

import de.daver.backup.io.FileHelper;
import de.daver.backup.util.Console;
import de.daver.backup.util.Properties;

import java.nio.file.Path;

public class ConfigurableCopyAction implements Action {

    @Override
    public boolean run() throws Exception {
        var configFile = Console.readInput("Please enter Path to config: ", Path::of);
        var properties = new Properties();
        properties.load(configFile);
        putDefaults(properties);

        var source = properties.get("source", Path::of);
        var destination = properties.get("destination", Path::of);
        var suffixes = properties.get("suffixes", FastCopyAction.SUFFIX_DESERIALIZER);
        var destinationType = properties.getEnumValue("destination-type", Destinations.class);

        FileHelper.copyFiles(source, destination, suffixes, destinationType);
        return false;
    }

    private void putDefaults(Properties properties) {

    }
}

package io.streamnative.utils;

import io.streamnative.config.AppConfig;
import io.streamnative.models.Event;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IngestionUtils {
    public static List<String> loadEventData() throws IOException {
        return readData(System.getProperty("user.dir") + AppConfig.RAW_EVENTS_FILE_PATH)
                .skip(1)
                .collect(Collectors.toList());
    }

    private static Stream<String> readData(String inputPath) throws IOException {
        Path path = Paths.get(inputPath);
        return Files.lines(path);
    }

    public static Event lineToEvent(String line) {
        String[] tokens = line.split(",", -1);
        Timestamp timestamp = Timestamp.valueOf(tokens[0].replace(" UTC", ""));

        return new Event(
                tokens[7],
                timestamp.getTime(),
                tokens[1],
                tokens[2],
                tokens[3],
                tokens[4],
                tokens[5],
                Double.parseDouble(tokens[6]),
                tokens[8]
        );
    }
}

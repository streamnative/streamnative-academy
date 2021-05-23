package sn.academy.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import sn.academy.config.AppConfig;
import sn.academy.models.StockTicker;

public class AppUtils {
    public static List<StockTicker> loadStockTickerData() throws IOException {
        return readData(System.getProperty("user.dir") + AppConfig.INPUT_FILE_PATH)
                .map(AppUtils::strToStockTicker)
                .collect(Collectors.toList());
    }

    public static Stream<String> readData(String inputPath) throws IOException {
        Path path = Paths.get(inputPath);
        return Files.lines(path);
    }

    public static StockTicker strToStockTicker(String str) {
        String[] tokens = str.split(",");
        Date date = Date.valueOf(tokens[0]);
        String name     = tokens[1];
        Double open     = strToDoubleParser(tokens[2]);
        Double close    = strToDoubleParser(tokens[3]);
        Double high     = strToDoubleParser(tokens[4]);
        Double low      = strToDoubleParser(tokens[5]);
        Double volume   = strToDoubleParser(tokens[6]);
        return new StockTicker(date, name, open, close, high, low, volume);
    }

    private static Double strToDoubleParser(String str) {
        return Optional.of(Double.parseDouble(str))
                .orElse(Double.NaN);
    }
}

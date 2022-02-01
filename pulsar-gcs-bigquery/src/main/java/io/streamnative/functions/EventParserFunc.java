package io.streamnative.functions;

import io.streamnative.models.Event;
import io.streamnative.utils.IngestionUtils;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;

public class EventParserFunc implements Function<String, Event> {
    private static Logger logger;

    @Override
    public Event process(String input, Context context) throws Exception {
        if (logger == null) {
            logger = context.getLogger();
        }
        logger.info("Received input: " + input);
        Event event = IngestionUtils.lineToEvent(input);
        logger.info("Parsed event: " + event);
        return event;
    }
}

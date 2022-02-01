package io.streamnative.producer;

import io.streamnative.config.AppConfig;
import io.streamnative.utils.ClientUtils;
import io.streamnative.utils.IngestionUtils;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class EventProducer {
    public static void main(String[] args) throws IOException {
        // 1. Load input data file
        List<String> events = IngestionUtils.loadEventData();

        // 2. Instantiate Pulsar Client
        PulsarClient pulsarClient = ClientUtils.initPulsarClient(Optional.empty());

        // 3. Create a Pulsar Producer
        Producer<String> eventProducer = pulsarClient.newProducer(Schema.STRING)
                .topic(AppConfig.RAW_EVENTS_TOPIC)
                .producerName("raw-events-producer")
                .blockIfQueueFull(true)
                .create();

        // 4. Send some messages
        for (String event: events) {
            eventProducer.newMessage()
                    .value(event)
                    .sendAsync()
                    .whenComplete(callback);
        }

        // 5. Add a shutdown hook to clear the resources
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    System.out.println("Closing producer and pulsar client..");
                    try {
                        eventProducer.close();
                        pulsarClient.close();
                    } catch (PulsarClientException e) {
                        e.printStackTrace();
                    }
                }));
    }

    private static final BiConsumer<MessageId, Throwable> callback = (id, ex) -> {
        if (ex != null) {
            System.out.println("Received exception: " + ex);
        } else {
            System.out.println("Acked message with id: " + id);
        }
    };
}

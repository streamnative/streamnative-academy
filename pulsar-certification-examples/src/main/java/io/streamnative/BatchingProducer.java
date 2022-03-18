package io.streamnative;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatchingProducer {
    private static final Logger logger = LoggerFactory.getLogger(BatchingProducer.class);
    private static AtomicInteger counter = new AtomicInteger(1);

    public static void main(String[] args) throws PulsarClientException {
        logger.info("Starting {} ...", BatchingProducer.class.getSimpleName());

        // 1. Instantiate pulsar client
        PulsarClient pulsarClient = ClientUtils.initPulsarClient(LabConfig.AUTH_TOKEN);

        // 2. Pulsar Producer
        Producer<String> producer = pulsarClient.newProducer(Schema.STRING)
                .topic(LabConfig.TEST_TOPIC)
                .producerName("test-producer")
                .enableBatching(true)
                .blockIfQueueFull(true)

                // A Batch is considered full either when maxMessages threshold (default=1000) it met or
                // maxBytes (default 128KB). You might want to tune them based on your message size to
                // make sure you have a good amount of messages within your batch

                //.batchingMaxMessages(10000)
                //.batchingMaxBytes(10000000)
                .create();

        // 3. Producer work
        for (int i = 1; i <= 1000000; i ++) {
            String payload = "msg-" + i;
            producer.newMessage()
                    .value(payload)
                    .sendAsync()
                    .whenComplete(getCallbackAction(payload));
        }

        // add a shutdown hook to clear the resources
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    logger.info("Closing Resources...");
                    try {
                        producer.close();
                        pulsarClient.close();
                    } catch (PulsarClientException e) {
                        e.printStackTrace();
                    }
                }));
    }

    private static BiConsumer<MessageId, Throwable> getCallbackAction(String payload) {
        return (msgId, exception) -> {
            if (exception != null) {
                logger.error("❌ Failed message: {}", exception.getMessage());
            } else {
                logger.info("✅ Acked message {} - Total messages {}", payload, counter.getAndIncrement());
            }
        };
    }
}

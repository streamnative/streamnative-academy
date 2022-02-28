package io.ipolyzos.consumers;

import io.ipolyzos.config.AppConfig;
import java.nio.charset.Charset;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;

public class EastClusterConsumer {
    public static void main(String[] args) throws PulsarClientException {
        PulsarClient pulsarClient = PulsarClient.builder()
                .serviceUrl(AppConfig.BROKER_URL_EAST)
                .build();

        Consumer<byte[]> consumer = pulsarClient.newConsumer(Schema.BYTES)
                .topic(AppConfig.topicName)
                .consumerName("test-consumer")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                .subscriptionName("test-subscription")
                .subscribe();

        int messageCount = 0;
        while (true) {
            Message<byte[]> message = consumer.receive();
            System.out.println("Received message: " + new String(message.getData(), Charset.defaultCharset()) + " - Total messages " + messageCount);
            messageCount += 1;
            try {
                consumer.acknowledge(message);
            } catch (Exception e) {
                consumer.negativeAcknowledge(message);
            }
        }
    }
}

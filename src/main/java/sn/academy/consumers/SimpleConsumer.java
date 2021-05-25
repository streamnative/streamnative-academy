package sn.academy.consumers;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import sn.academy.config.AppConfig;
import sn.academy.models.StockTicker;
import sn.academy.utils.ClientUtils;

public class SimpleConsumer {
    public static void main(String[] args) throws PulsarClientException {
        PulsarClient pulsarClient = ClientUtils.initPulsarClient();

        Consumer<StockTicker> consumer = pulsarClient.newConsumer(JSONSchema.of(StockTicker.class))
                .topic(AppConfig.singleTopic)
                .consumerName("stock-tickers-consumer")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                .subscriptionName("test-subscription")
                .subscribe();

        int messageCount = 0;
        while (true) {
            Message<StockTicker> message = consumer.receive();

            messageCount += 1;
            System.out.println("Acked message [" + message.getMessageId() + "], Total messages acked so far: " + messageCount);
            try {
                consumer.acknowledge(message);
            } catch (Exception e) {
                consumer.negativeAcknowledge(message);
            }
        }
    }
}

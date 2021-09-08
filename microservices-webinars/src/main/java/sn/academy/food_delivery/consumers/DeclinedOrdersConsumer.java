package sn.academy.food_delivery.consumers;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionInitialPosition;
import org.apache.pulsar.client.impl.schema.AvroSchema;
import sn.academy.food_delivery.config.AppConfig;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;
import sn.academy.food_delivery.utils.ClientUtils;

public class DeclinedOrdersConsumer {
    public static void main(String[] args) throws PulsarClientException {
        PulsarClient pulsarClient = ClientUtils.initPulsarClient(AppConfig.DEV_TOKEN);
        Consumer<ValidatedFoodOrder> consumer = pulsarClient.newConsumer(AvroSchema.of(ValidatedFoodOrder.class))
                .topic(AppConfig.DECLINED_ORDERS_TOPIC_NAME)
                .consumerName("declined-orders-consumer")
                .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                .subscriptionName("declined-orders-subscription2")
                .subscribe();

        int messageCount = 0;
        while (true) {
            Message<ValidatedFoodOrder> message = consumer.receive();

            messageCount += 1;
            System.out.println(message.getValue());
            System.out.println(message.getProperties());
            try {
                consumer.acknowledge(message);
                System.out.println("Acked message [" + message.getMessageId() + "], Total messages acked so far: " + messageCount);
            } catch (Exception e) {
                consumer.negativeAcknowledge(message);
            }
        }
    }
}

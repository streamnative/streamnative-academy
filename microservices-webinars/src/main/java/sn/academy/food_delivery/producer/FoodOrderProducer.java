package sn.academy.food_delivery.producer;

import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.interceptor.ProducerInterceptor;
import org.apache.pulsar.client.impl.schema.AvroSchema;
import sn.academy.food_delivery.config.AppConfig;
import sn.academy.food_delivery.models.avro.FoodOrder;
import sn.academy.food_delivery.source.FoodOrderGenerator;
import sn.academy.food_delivery.utils.ClientUtils;

public class FoodOrderProducer {
    public static void main(String[] args) throws PulsarClientException, InterruptedException {
        FoodOrderGenerator foodOrderGenerator = new FoodOrderGenerator();

        PulsarClient pulsarClient = ClientUtils.initPulsarClient(AppConfig.DEV_TOKEN);

        Producer<FoodOrder> foodOrderProducer = pulsarClient.newProducer(AvroSchema.of(FoodOrder.class))
                .producerName("food-order-producer")
                .topic(AppConfig.FOOD_ORDERS_TOPIC_NAME)
                .intercept(new CustomProducerInterceptor())
                .create();

        // add a shutdown hook to clear the resources
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    System.out.println("Closing producer and pulsar client..");
                    try {
                        foodOrderProducer.close();
                        pulsarClient.close();
                    } catch (PulsarClientException e) {
                        e.printStackTrace();
                    }
                }));

        while (true) {
            Thread.sleep(500);
            FoodOrder foodOrder = foodOrderGenerator.generateOrder();
            System.out.println(foodOrder);
            foodOrderProducer.newMessage()
                    .value(foodOrder)
                    .eventTime(System.currentTimeMillis())
                    .sendAsync();
        }
    }

    static class CustomProducerInterceptor implements ProducerInterceptor {

        @Override
        public void close() {}

        @Override
        public boolean eligible(Message message) {
            return true;
        }

        @Override
        public Message beforeSend(Producer producer, Message message) {
            return message;
        }

        @Override
        public void onSendAcknowledgement(Producer producer, Message message, MessageId msgId, Throwable exception) {
            String msg = String.format(
                    "[%s] acknowledged message with id '%s'.",
                    producer.getProducerName(),
                    msgId
            );
            System.out.println(msg);
        }
    }
}

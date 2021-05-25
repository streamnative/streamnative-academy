package sn.academy.utils;

import org.apache.pulsar.client.api.*;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import sn.academy.models.StockTicker;

import java.util.concurrent.atomic.AtomicInteger;

public class ConsumerThread extends Thread {
    private Consumer<StockTicker> consumer;
    private final AtomicInteger totalMessages = new AtomicInteger();
    private int delay = 0;
    private boolean isRunning = true;

    public ConsumerThread(PulsarClient pulsarClient,
                          String topicName,
                          String subscriptionName,
                          String consumerName,
                          SubscriptionType subscriptionType) {
        try {
            this.consumer =  pulsarClient.newConsumer(JSONSchema.of(StockTicker.class))
                    .topic(topicName)
                    .subscriptionName(subscriptionName)
                    .subscriptionType(subscriptionType)
                    .consumerName(consumerName)
                    .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                    .subscribe();
            registerShutdownHook();
        } catch (PulsarClientException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            if (delay != 0) {
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message msg = null;
            try {
                msg = consumer.receive();
                if (msg != null) {
                    consumer.acknowledge(msg);
                    System.out.println("[" + consumer.getConsumerName() + "] Received message: " + msg.getValue());
                    System.out.println("[" + consumer.getConsumerName() + "] Total messages received: " + totalMessages.addAndGet(1));
                }
            } catch (PulsarClientException e) {
                consumer.negativeAcknowledge(msg);
            }
        }
        try {
            this.consumer.close();
            System.out.println(consumer.getConsumerName() + " disconnected");
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }

    public void addDelay(int delay) {
        this.delay = delay;
    }

    public void disconnectConsumer() {
        this.isRunning = false;
    }


    private void registerShutdownHook() {
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    System.out.println("[" + consumer.getConsumerName() + "] Disconnecting..");
                    try {
                        consumer.close();
                    } catch (PulsarClientException e) {
                        e.printStackTrace();
                    }
                }));
    }
}

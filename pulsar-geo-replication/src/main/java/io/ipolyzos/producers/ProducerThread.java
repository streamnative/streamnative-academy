package io.ipolyzos.producers;

import io.ipolyzos.config.AppConfig;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.Schema;

public class ProducerThread {
    private final int totalMessages = 300;
    private int messagesSoFar = 1;
    private String BROKER_URL = AppConfig.BROKER_URL_WEST;

    private PulsarClient pulsarClient = null;
    private Producer<byte[]> producer = null;

    public ProducerThread() {}

    public boolean initClient() throws PulsarClientException {
        pulsarClient = PulsarClient.builder()
                .serviceUrl(BROKER_URL)
                .build();
        return pulsarClient.isClosed();
    }

    public boolean initProducer() throws PulsarClientException {
        initClient();
        this.producer = pulsarClient.newProducer(Schema.BYTES)
                .topic(AppConfig.topicName)
                .create();
        return producer.isConnected();
    }

    public void runProducer() throws PulsarClientException {
        pulsarClient.newProducer(Schema.BYTES)
                .topic(AppConfig.topicName)
                .create();

        for (int i = messagesSoFar; i <= totalMessages; i++) {
            System.out.println("Sending msg-" + i);
            producer.newMessage()
                    .value(("msg-" + i).getBytes())
                    .send();
            messagesSoFar += 1;
        }
    }

    public void fallback() {
        this.BROKER_URL = AppConfig.BROKER_URL_CENTRAL;
        System.out.println("Attempting Connection with the fallback cluster: " + this.BROKER_URL);
    }

    public boolean isConnected() {
        return producer.isConnected();
    }
    public String getConnectionURL() {
        return this.BROKER_URL;
    }
}

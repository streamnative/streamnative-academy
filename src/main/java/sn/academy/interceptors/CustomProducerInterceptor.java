package sn.academy.interceptors;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.interceptor.ProducerInterceptor;

public class CustomProducerInterceptor implements ProducerInterceptor {
    private final AtomicInteger totalMessages = new AtomicInteger();

    @Override
    public void close() {}

    @Override
    public boolean eligible(Message message) {
        return true;
    }

    @Override
    public Message beforeSend(Producer producer, Message message) {
        System.out.println("[" + producer.getProducerName() + "] Sending message with payload: " + new String(message.getData()));
        return message;
    }

    @Override
    public void onSendAcknowledgement(Producer producer, Message message, MessageId messageId, Throwable throwable) {
        if (throwable != null) {
            throw new RuntimeException(throwable.toString());
        }
        System.out.println("[" + producer.getProducerName() + "] Acked message - "+ messageId + " - with payload: " + new String(message.getData()));
        totalMessages.addAndGet(1);
    }

    public AtomicInteger totalMessageCount() {
        return totalMessages;
    }
}

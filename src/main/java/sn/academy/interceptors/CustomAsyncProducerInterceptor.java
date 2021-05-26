package sn.academy.interceptors;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.interceptor.ProducerInterceptor;

public class CustomAsyncProducerInterceptor implements ProducerInterceptor {
    private final AtomicInteger totalMessages = new AtomicInteger();
    private final long t1 = System.currentTimeMillis();

    @Override
    public void close() {}

    @Override
    public boolean eligible(Message message) {
        return true;
    }

    @Override
    public Message beforeSend(Producer producer, Message message) {
        return message;    }

    @Override
    public void onSendAcknowledgement(Producer producer, Message message, MessageId messageId, Throwable throwable) {
        if (throwable != null) {
            throw new RuntimeException(throwable.toString());
        }
        System.out.println("[" + producer.getProducerName() + "] Acked message - "+ messageId + " - with payload: " + new String(message.getData()));

        long t2 = System.currentTimeMillis();
        long totalTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(t2 - t1);
        System.out.printf("Total messages produced: %s in %s seconds.%n", totalMessages.getAndAdd(1), totalTimeSeconds);
    }

    public AtomicInteger totalMessageCount() {
        return totalMessages;
    }

}

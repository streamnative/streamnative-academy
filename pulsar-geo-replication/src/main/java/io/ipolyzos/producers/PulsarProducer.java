package io.ipolyzos.producers;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.ipolyzos.config.AppConfig;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import java.time.Duration;
import org.apache.pulsar.client.api.PulsarClientException;

public class PulsarProducer {

    public static void main(String[] args) {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(AppConfig.maxAttempts)
                .waitDuration(Duration.ofMillis(1))
                .retryOnResult(response -> response instanceof PulsarClientException)
                .failAfterMaxAttempts(true)
                .build();

        RetryRegistry registry = RetryRegistry.of(config);
        Retry retry = registry.retry("myretry");

        ProducerThread producerThread = new ProducerThread();

        CheckedFunction0<Boolean> clientConnection  = Retry
                .decorateCheckedSupplier(retry, producerThread::initProducer);

        Try<Boolean> result = Try.of(clientConnection)
                .andThenTry(producerThread::runProducer)
                .onFailure(throwable -> {
                    if (throwable instanceof PulsarClientException) {
                        System.out.printf(
                                "An exception occured, while trying to connect to cluster: %s - failed after %s "
                                        + "retries.%n",
                                producerThread.getConnectionURL(),
                                AppConfig.maxAttempts
                                );
                        producerThread.fallback();
                    } else {
                        throw new RuntimeException(throwable);
                    }
                }).recover((throwable -> {
                    try {
                        producerThread.initProducer();
                        producerThread.runProducer();
                        return producerThread.isConnected();
                    } catch (PulsarClientException e) {
                        System.out.println("None of the clusters seems reachable, Giving up ... ");
                        return false;
                    }
                }));

        if (!result.get()) {
            System.exit(0);
        }
    }
}

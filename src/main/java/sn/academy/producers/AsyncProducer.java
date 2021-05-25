package sn.academy.producers;

import java.io.IOException;
import java.util.List;
import org.apache.pulsar.client.api.BatcherBuilder;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import sn.academy.config.AppConfig;
import sn.academy.interceptors.CustomAsyncProducerInterceptor;
import sn.academy.models.StockTicker;
import sn.academy.utils.AppUtils;

public class AsyncProducer {
    public static void main(String[] args) throws IOException {
        List<StockTicker> stockTickers = AppUtils.loadStockTickerData();

        // 1. Instantiate pulsar client
        PulsarClient pulsarClient = PulsarClient.builder()
                .serviceUrl(AppConfig.SERVICE_URL)
                .build();

        CustomAsyncProducerInterceptor interceptor = new CustomAsyncProducerInterceptor();
        // 2. Pulsar Producer
        Producer<StockTicker> producer = pulsarClient.newProducer(JSONSchema.of(StockTicker.class))
                .topic(AppConfig.singleTopic)
                .producerName("stock-ticker-producer")
                .intercept(interceptor)
                .enableBatching(true)
                .batcherBuilder(BatcherBuilder.KEY_BASED)
                .blockIfQueueFull(true)
                .maxPendingMessages(50000)
                .create();

        System.out.println("Running Producer work loop");
        // 3. Initiate a producer work loop
        for (StockTicker st: stockTickers) {
            producer.newMessage(JSONSchema.of(StockTicker.class))
                    .key(st.getName())
                    .value(st)
                    .sendAsync();
        }

        // add a shutdown hook to clear the resources
        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    System.out.println("Closing producer and pulsar client..");
                    try {
                        producer.close();
                        pulsarClient.close();
                    } catch (PulsarClientException e) {
                        e.printStackTrace();
                    }
                }));
    }
}

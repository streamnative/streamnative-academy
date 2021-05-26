package sn.academy.producers;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import sn.academy.config.AppConfig;
import sn.academy.interceptors.CustomProducerInterceptor;
import sn.academy.models.StockTicker;
import sn.academy.utils.AppUtils;

public class SyncProducer {
    public static void main(String[] args) throws IOException {
        List<StockTicker> stockTickers = AppUtils.loadStockTickerData();

        // 1. Instantiate pulsar client
        PulsarClient pulsarClient = PulsarClient.builder()
                .serviceUrl(AppConfig.SERVICE_URL)
                .build();

        CustomProducerInterceptor interceptor = new CustomProducerInterceptor();
        // 2. Pulsar Producer
        Producer<StockTicker> producer = pulsarClient.newProducer(JSONSchema.of(StockTicker.class))
                .topic(AppConfig.singleTopic)
                .producerName("stock-ticker-producer")
                .intercept(interceptor)
                .create();

        System.out.println("Running Producer work loop");
        long t1 = System.currentTimeMillis();
        // 3. Initiate a producer work loop
        for (StockTicker st: stockTickers) {
            producer.newMessage(JSONSchema.of(StockTicker.class))
                    .key(st.getName())
                    .value(st)
                    .send();
        }

        long t2 = System.currentTimeMillis();
        long totalTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(t2 - t1);
        producer.flush();
        System.out.printf("Total messages produced: %s in %s seconds.%n", interceptor.totalMessageCount(), totalTimeSeconds);

        // 4. Close all the resources
        producer.close();
        pulsarClient.close();
    }
}

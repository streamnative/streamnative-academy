package sn.academy.consumers;

import java.util.stream.IntStream;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import sn.academy.config.AppConfig;
import sn.academy.utils.ClientUtils;
import sn.academy.utils.ConsumerThread;

public class KeysharedConsumer {
    public static void main(String[] args) throws PulsarClientException {
        PulsarClient pulsarClient = ClientUtils.initPulsarClient();

        /**
         *  9760 APPL
         *  8433 MSFT
         *  5607 AMZN
         *  3780 GOOGL
         *  1828 FB
         * */
        IntStream.of(1, 2, 3, 4, 5).mapToObj(id -> {
            return new ConsumerThread(
                    pulsarClient, AppConfig.singleTopic,
                    "test-subscription",
                    "consumer-" + id,
                    SubscriptionType.Key_Shared);
        }).forEach(Thread::start);
    }
}

package sn.academy.consumers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import sn.academy.config.AppConfig;
import sn.academy.utils.ClientUtils;
import sn.academy.utils.ConsumerThread;

public class FailoverConsumer {
    public static void main(String[] args) throws PulsarClientException, InterruptedException {
        PulsarClient pulsarClient = ClientUtils.initPulsarClient();

        List<ConsumerThread> consumerThreads = IntStream.of(1, 2, 3).mapToObj(id -> {
            ConsumerThread consumerThread = new ConsumerThread(
                    pulsarClient, AppConfig.singleTopic,
                    "test-subscription",
                    "consumer-" + id,
                    SubscriptionType.Failover);
            consumerThread.addDelay(250);
            return consumerThread;
        }).collect(Collectors.toList());

        consumerThreads.forEach(Thread::start);

        for (int i = 1; i < consumerThreads.size(); i ++) {
            Thread.sleep(10000);
            consumerThreads.get(i - 1).disconnectConsumer();
        }
    }
}

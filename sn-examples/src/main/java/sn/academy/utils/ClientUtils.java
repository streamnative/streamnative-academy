package sn.academy.utils;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import sn.academy.config.AppConfig;

public class ClientUtils {
    public static PulsarClient initPulsarClient() throws PulsarClientException {
        return PulsarClient.builder()
                .serviceUrl(AppConfig.SERVICE_URL)
                .build();
    }

}

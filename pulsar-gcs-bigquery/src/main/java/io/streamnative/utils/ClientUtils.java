package io.streamnative.utils;

import io.streamnative.config.AppConfig;
import java.util.Optional;
import org.apache.pulsar.client.api.ClientBuilder;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.auth.AuthenticationToken;

/**
 * Helper class that provides some utilities for the Pulsar Client
 * */
public class ClientUtils {
    public static PulsarClient initPulsarClient(Optional<String> token) throws PulsarClientException {
        ClientBuilder clientBuilder = PulsarClient.builder().serviceUrl(AppConfig.SERVICE_URL);
        if (token.isPresent()) {
            AuthenticationToken authenticationToken = new AuthenticationToken(token.get());
            clientBuilder.authentication(authenticationToken);
        }
        return clientBuilder.build();
    }
}
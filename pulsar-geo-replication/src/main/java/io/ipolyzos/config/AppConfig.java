package io.ipolyzos.config;

public class AppConfig {
    public static final String BROKER_URL_WEST = "pulsar://localhost:6650";
    public static final String BROKER_URL_EAST = "pulsar://localhost:6651";
    public static final String BROKER_URL_CENTRAL = "pulsar://localhost:6652";

    public static final String topicName = "persistent://testt/testns/t1";
    public static final int maxAttempts = 1;
}

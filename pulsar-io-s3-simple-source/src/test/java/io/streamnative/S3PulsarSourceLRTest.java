package io.streamnative;

import java.util.HashMap;
import java.util.Map;
import org.apache.pulsar.common.functions.ProducerConfig;
import org.apache.pulsar.common.io.SourceConfig;
import org.apache.pulsar.functions.LocalRunner;

public class S3PulsarSourceLRTest {
    private static final String EVENTS_BUCKET_NAME = "events-bucket-small";
    private static final String EVENTS_OUTPUT_TOPIC_NAME = "persistent://public/default/raw_click_events";


    public static void main(String[] args) throws Exception {
        startLocalRunner();
    }

    private static void startLocalRunner() throws Exception {
        LocalRunner localRunner = LocalRunner.builder()
                .brokerServiceUrl("pulsar://127.0.0.1:6650")
                .sourceConfig(getSourceConfig())
                .build();
        localRunner.start(false);
    }


    private static SourceConfig getSourceConfig() {
        Map<String, Object> secrets = new HashMap<>();
        secrets.put("AWS_ACCESS_KEY", "");
        secrets.put("AWS_SECRET_KEY", "");

        Map<String, Object> configs = new HashMap<>();
        configs.put("awsBuckets", EVENTS_BUCKET_NAME);
        configs.put("awsRegion", "us-west-2");

        ProducerConfig producerConfig = new ProducerConfig();
        producerConfig.setMaxPendingMessages(2000);
        producerConfig.setMaxPendingMessagesAcrossPartitions(2000);
        return SourceConfig.builder()
                .className(S3PulsarSource.class.getName())
                .configs(configs)
                .secrets(secrets)
                .name("s3-io-source")
                .tenant("public")
                .namespace("default")
                .topicName(EVENTS_OUTPUT_TOPIC_NAME)
                .producerConfig(producerConfig)
                .build();
    }
}

package io.streamnative;

import io.streamnative.config.AppConfig;
import io.streamnative.functions.EventParserFunc;
import java.util.Collections;
import java.util.HashMap;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.common.functions.ConsumerConfig;
import org.apache.pulsar.common.functions.FunctionConfig;
import org.apache.pulsar.functions.LocalRunner;

public class EventParserFuncTest {
    public static void main(String[] args) throws Exception {
        HashMap<String, ConsumerConfig> inputSpecs = new HashMap<>();
        inputSpecs.put(
                AppConfig.RAW_EVENTS_TOPIC, ConsumerConfig.builder()
                        .schemaType(
                                Schema.STRING.getSchemaInfo().getName()
                        ).build()
        );

        FunctionConfig functionConfig = FunctionConfig.builder()
                .className(EventParserFunc.class.getName())
                .inputs(Collections.singletonList(AppConfig.RAW_EVENTS_TOPIC))
                .inputSpecs(inputSpecs)
                .output(AppConfig.PARSED_EVENTS_TOPIC)
                .name("event-parser-func")
                .runtime(FunctionConfig.Runtime.JAVA)
                .subName("raw-events-subscription")
                .cleanupSubscription(true)
                .build();

        LocalRunner localRunner = LocalRunner.builder()
                .brokerServiceUrl(AppConfig.SERVICE_URL)
                .functionConfig(functionConfig)
                .build();

        localRunner.start(false);
    }
}

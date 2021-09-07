package sn.academy.food_delivery.services.processing;

import java.util.Collections;
import java.util.HashMap;
import org.apache.pulsar.common.functions.ConsumerConfig;
import org.apache.pulsar.common.functions.FunctionConfig;
import org.apache.pulsar.functions.LocalRunner;
import sn.academy.food_delivery.config.AppConfig;

public class GeoEncoderServiceLRTest {
    public static void main(String[] args) throws Exception {
        HashMap<String, ConsumerConfig> inputSpecs = new HashMap<>();
        inputSpecs.put(
                AppConfig.GEO_ENCODER_TOPIC_NAME, ConsumerConfig.builder().schemaType("AVRO").build()
        );

        FunctionConfig functionConfig = FunctionConfig.builder()
                .className(GeoEncoderService.class.getName())
                .inputs(Collections.singletonList(AppConfig.GEO_ENCODER_TOPIC_NAME))
                .inputSpecs(inputSpecs)
                .name("geo-encoder")
                .runtime(FunctionConfig.Runtime.JAVA)
                .subName("geo-encoding-subscription")
                .cleanupSubscription(true)
                .build();

        LocalRunner localRunner = LocalRunner.builder()
                .brokerServiceUrl(AppConfig.SERVICE_URL)
                .functionConfig(functionConfig)
                .build();

        localRunner.start(false);
//        Thread.sleep(30000);
//        localRunner.stop();
//        System.exit(0);
    }
}

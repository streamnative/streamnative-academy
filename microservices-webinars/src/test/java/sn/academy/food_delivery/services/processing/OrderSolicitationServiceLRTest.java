package sn.academy.food_delivery.services.processing;

import java.util.Collections;
import java.util.HashMap;
import org.apache.pulsar.common.functions.ConsumerConfig;
import org.apache.pulsar.common.functions.FunctionConfig;
import org.apache.pulsar.functions.LocalRunner;
import sn.academy.food_delivery.config.AppConfig;

public class OrderSolicitationServiceLRTest {
    public static void main(String[] args) throws Exception {
        HashMap<String, ConsumerConfig> inputSpecs = new HashMap<>();
        inputSpecs.put(AppConfig.RESTAURANTS_TOPIC_NAME, ConsumerConfig.builder().schemaType("avro").build());

        FunctionConfig functionConfig = FunctionConfig.builder()
                .className(OrderSolicitationService.class.getName())
                .inputs(Collections.singletonList(AppConfig.RESTAURANTS_TOPIC_NAME))
                .inputSpecs(inputSpecs)
                .name("order-solicitation")
                .runtime(FunctionConfig.Runtime.JAVA)
                .subName("order-solicitation-subscription")
                .cleanupSubscription(true)
                .build();

        LocalRunner localRunner = LocalRunner.builder()
                .brokerServiceUrl(AppConfig.SERVICE_URL)
                .functionConfig(functionConfig)
                .build();

        localRunner.start(false);
        Thread.sleep(30000);
        localRunner.stop();
        System.exit(0);
    }
}

package sn.academy.food_delivery.services.validation;

import java.util.Collections;
import java.util.HashMap;
import org.apache.pulsar.common.functions.ConsumerConfig;
import org.apache.pulsar.common.functions.FunctionConfig;
import org.apache.pulsar.functions.LocalRunner;
import sn.academy.food_delivery.config.AppConfig;

public class OrderValidationServiceLRTest  {
    public static void main(String[] args) throws Exception {
        HashMap<String, ConsumerConfig> inputSpecs = new HashMap<>();
        inputSpecs.put(AppConfig.FOOD_ORDERS_TOPIC_NAME, ConsumerConfig.builder().schemaType("avro").build());

        FunctionConfig functionConfig = FunctionConfig.builder()
                .className(OrderValidationService.class.getName())
                .inputs(Collections.singletonList(AppConfig.FOOD_ORDERS_TOPIC_NAME))
                .inputSpecs(inputSpecs)
                .name("order-validation")
                .runtime(FunctionConfig.Runtime.JAVA)
                .subName("order-validation-subscription")
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

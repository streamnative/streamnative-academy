package sn.academy.food_delivery.source;

import org.apache.pulsar.common.io.SourceConfig;
import org.apache.pulsar.functions.LocalRunner;
import sn.academy.food_delivery.config.AppConfig;

public class FoodOrderSourceLRTest {
    public static void main(String[] args) throws Exception {
        SourceConfig sourceConfig = SourceConfig
                .builder()
                .className(FoodOrderSource.class.getName())
                .name("food-order-generator")
                .topicName(AppConfig.FOOD_ORDERS_TOPIC_NAME)
                .schemaType("avro")
                .build();

        LocalRunner localRunner = LocalRunner.builder()
                .brokerServiceUrl(AppConfig.SERVICE_URL)
                .sourceConfig(sourceConfig)
                .build();

        localRunner.start(false);

        Thread.sleep(30000);
        localRunner.stop();
        System.exit(0);
    }
}

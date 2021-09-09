package sn.academy.food_delivery.config;

import java.util.Optional;

public class AppConfig {
    public static final String SERVICE_URL                  = "pulsar+ssl://data.workshop.aws.sn3.dev:6651";
    public static final String STATE_STORAGE_SERVICE_URL    = "bk://localhost:4181";

    public static final String FOOD_ORDERS_TOPIC_NAME       = "persistent://orders/inbound/food-orders";
    public static final String GEO_ENCODER_TOPIC_NAME       = "persistent://orders/validation/geo-encoder";
    public static final String PAYMENTS_TOPIC_NAME          = "persistent://orders/validation/payments";
    public static final String RESTAURANTS_TOPIC_NAME       = "persistent://orders/validation/restaurants";
    public static final String ORDER_AGGREGATION_TOPIC_NAME = "persistent://orders/validation/aggregated-orders";

    public static final String ACCEPTED_ORDERS_TOPIC_NAME   = "persistent://orders/outbound/orders-accepted";
    public static final String DECLINED_ORDERS_TOPIC_NAME   = "persistent://orders/outbound/orders-declined";

    public static final Optional<String> DEV_TOKEN          = Optional.empty();
}

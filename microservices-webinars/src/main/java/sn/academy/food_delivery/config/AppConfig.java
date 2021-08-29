package sn.academy.food_delivery.config;

public class AppConfig {
    public static final String SERVICE_URL = "pulsar://localhost:6650"

    public static final String FOOD_ORDERS_TOPIC_NAME = "persistent://orders/inbound/food-orders";
    public static final String GEO_ENCODER_TOPIC_NAME = "persistent://orders/inbound/geo-encoder";
    public static final String PAYMENTS_TOPIC_NAME = "persistent://orders/inbound/payments";
    public static final String RESTAURANTS_TOPIC_NAME = "persistent://orders/inbound/restaurants";
    public static final String ORDER_AGGREGATION_TOPIC_NAME = "persistent://orders/inbound/aggregated-orders";

    public static final String ACCEPTED_ORDERS_TOPIC_NAME = "persistent://orders/inbound/orders-accepted";
    public static final String DECLINED_ORDERS_TOPIC_NAME = "persistent://orders/inbound/orders-declined";
}

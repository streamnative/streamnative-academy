package sn.academy.food_delivery.config;

public class AppConfig {
    public static final String SERVICE_URL = "pulsar://localhost:6650";

    public static final String FOOD_ORDERS_TOPIC_NAME = "persistent://orders/inbound/food-orders";
}

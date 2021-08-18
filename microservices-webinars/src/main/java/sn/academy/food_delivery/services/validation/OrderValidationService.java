package sn.academy.food_delivery.services.validation;

import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.config.AppConfig;
import sn.academy.food_delivery.models.avro.Address;
import sn.academy.food_delivery.models.avro.CreditCard;
import sn.academy.food_delivery.models.avro.FoodOrder;
import sn.academy.food_delivery.models.avro.FoodOrderMeta;
import sn.academy.food_delivery.models.avro.Payment;

/**
 * Demonstrates the Splitter Pattern
 *
 * */
public class OrderValidationService implements Function<FoodOrder, Void> {
    private Logger logger;

    @Override
    public Void process(FoodOrder foodOrder, Context context) throws Exception {
        logger = context.getLogger();

        // When a new order is invoke 3 services to validate it
        String orderId = foodOrder.getMeta().getOrderId() + "";
        Address deliveryLocation = foodOrder.getDeliveryLocation();
        Payment payment = foodOrder.getPayment();

        // invoke the geo-encoding service
        context.newOutputMessage(AppConfig.GEO_ENCODER_TOPIC_NAME, AvroSchema.of(Address.class))
                .property("order-id", orderId)
                .value(deliveryLocation)
                .sendAsync();

        // invoke the payments service
        context.newOutputMessage(AppConfig.PAYMENTS_TOPIC_NAME, AvroSchema.of(Payment.class))
                .property("order-id", orderId)
                .value(payment)
                .sendAsync();

        context.newOutputMessage(AppConfig.ORDER_AGGREGATION_TOPIC_NAME, AvroSchema.of(FoodOrderMeta.class))
                .property("order-id", orderId)
                .value(foodOrder.getMeta())
                .sendAsync();

        context.newOutputMessage(AppConfig.RESTAURANTS_TOPIC_NAME, AvroSchema.of(FoodOrder.class))
                .property("order-id", orderId)
                .value(foodOrder)
                .sendAsync();

        logger.info("Received: " + foodOrder);
        return null;
    }
}

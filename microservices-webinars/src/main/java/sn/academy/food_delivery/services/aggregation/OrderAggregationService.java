package sn.academy.food_delivery.services.aggregation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.config.AppConfig;
import sn.academy.food_delivery.models.avro.OrderStatus;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;

public class OrderAggregationService implements Function<ValidatedFoodOrder, Void> {
    private Logger logger;
    private Map<String, Set<ValidatedFoodOrder>> orderCache = new HashMap<>();

    @Override
    public Void process(ValidatedFoodOrder validatedFoodOrder, Context context) throws Exception {
        logger = context.getLogger();
        logger.info(validatedFoodOrder.toString());

        String orderId = context.getCurrentRecord().getProperties().get("order-id");

        Set<ValidatedFoodOrder> orderResponses = orderCache.getOrDefault(orderId, new HashSet<>());
        orderResponses.add(validatedFoodOrder);
        if (orderResponses.size() == 4) {
            ValidatedFoodOrder aggregatedOrderValidation = aggregateResponses(validatedFoodOrder, orderResponses);
            logger.info("Received all responses for order: " + aggregatedOrderValidation);
            orderCache.remove(orderId);
            if (validatedFoodOrder.getMeta().getOrderStatus()== OrderStatus.ACCEPTED) {
                context.newOutputMessage(AppConfig.ACCEPTED_ORDERS_TOPIC_NAME, AvroSchema.of(ValidatedFoodOrder.class))
                        .value(aggregatedOrderValidation)
                        .sendAsync();
            } else {
                context.newOutputMessage(AppConfig.DECLINED_ORDERS_TOPIC_NAME, AvroSchema.of(ValidatedFoodOrder.class))
                        .value(aggregatedOrderValidation)
                        .sendAsync();
            }
        } else {
            orderCache.put(orderId, orderResponses);
        }
        logger.info("Total Records In Cache: " + orderCache.size());
        // TODO: Clear cache every x minutes, to make sure it doesn't grow from missing messages
        return null;
    }

    private ValidatedFoodOrder aggregateResponses(ValidatedFoodOrder validatedFoodOrder, Set<ValidatedFoodOrder> orderResponses) {
        for (ValidatedFoodOrder orderResponse: orderResponses) {
            if (orderResponse.getMeta() != null) {
                validatedFoodOrder.setMeta(orderResponse.getMeta());
            }
            if (orderResponse.getRestaurantId() != null) {
                validatedFoodOrder.setRestaurantId(orderResponse.getRestaurantId());
            }
            if (orderResponse.getDetails() != null) {
                validatedFoodOrder.setDetails(orderResponse.getDetails());
            }
            if (orderResponse.getEta() != null) {
                validatedFoodOrder.setEta(orderResponse.getEta());
            }
            if (orderResponse.getDeliveryLocation() != null) {
                validatedFoodOrder.setDeliveryLocation(orderResponse.getDeliveryLocation());
            }
            if (orderResponse.getPayment() != null) {
                validatedFoodOrder.setPayment(orderResponse.getPayment());
            }
        }
        return validatedFoodOrder;
    }
}

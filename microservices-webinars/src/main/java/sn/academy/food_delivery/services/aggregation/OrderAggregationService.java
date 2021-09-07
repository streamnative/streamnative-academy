package sn.academy.food_delivery.services.aggregation;

import java.nio.ByteBuffer;
import org.apache.commons.lang.SerializationUtils;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;

import sn.academy.food_delivery.config.AppConfig;
import sn.academy.food_delivery.models.avro.OrderStatus;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;

/**
 * This class demonstrates the aggregator pattern using Pulsar State Store
 *
 * @see https://www.enterpriseintegrationpatterns.com/Aggregator.html
 * */
public class OrderAggregationService implements Function<ValidatedFoodOrder, Void> {
    private Logger logger;

    @Override
    public Void process(ValidatedFoodOrder validatedFoodOrder, Context context) throws Exception {
        logger = context.getLogger();
        String orderId = context.getCurrentRecord().getProperties().get("order-id");

        ByteBuffer currentState = context.getState(orderId);
        if (currentState == null) {
            logger.info("Initializing state for order: " + orderId);
            context.putState(orderId, ByteBuffer.wrap(SerializationUtils.serialize(validatedFoodOrder)));
        } else {
            ValidatedFoodOrder orderState = (ValidatedFoodOrder) SerializationUtils.deserialize(currentState.array());

            ValidatedFoodOrder updatedOrder = updateOrderFields(orderState, validatedFoodOrder);
            if (checkIfAggregationCompleted(updatedOrder)) {
                logger.info("Order " + orderId + " completed - " + updatedOrder);
                routeValidatedOrder(context, updatedOrder);
                context.deleteStateAsync(orderId).thenAccept(
                        unused -> System.out.println("Order " + orderId + " state deleted."));
            } else {
                context.putState(orderId, ByteBuffer.wrap(SerializationUtils.serialize(updatedOrder)));
            }
        }
       return null;
    }

    private ValidatedFoodOrder updateOrderFields(ValidatedFoodOrder orderState, ValidatedFoodOrder inputOrder) {
        if (inputOrder.getDetails() != null) {
            orderState.setDetails(inputOrder.getDetails());
        }

        if (inputOrder.getMeta() != null) {
            orderState.setMeta(inputOrder.getMeta());
        }

        if (inputOrder.getDeliveryLocation() != null) {
            orderState.setDeliveryLocation(inputOrder.getDeliveryLocation());
        }

        if (inputOrder.getEta() != null) {
            orderState.setEta(inputOrder.getEta());
        }

        if (inputOrder.getPayment() != null) {
            orderState.setPayment(inputOrder.getPayment());
        }

        if (inputOrder.getRestaurantId() != null) {
            orderState.setRestaurantId(inputOrder.getRestaurantId());
        }
        return orderState;
    }

    private boolean checkIfAggregationCompleted(ValidatedFoodOrder order) {
        return order.getDetails() != null &&
                order.getMeta() != null &&
                order.getDeliveryLocation() != null &&
                order.getEta() != null &&
                order.getPayment() != null
                && order.getRestaurantId() != null;
    }

    private void routeValidatedOrder(Context context, ValidatedFoodOrder order) throws PulsarClientException {
        if (order.getPayment().getIsAuthorized() && order.getMeta().getOrderStatus() == OrderStatus.ACCEPTED) {
            context.newOutputMessage(AppConfig.ACCEPTED_ORDERS_TOPIC_NAME, AvroSchema.of(ValidatedFoodOrder.class))
                    .value(order)
                    .sendAsync();
        } else {
            String message = "restaurant-declined";
            if (!order.getPayment().getIsAuthorized()) {
                message = "payment-unauthorized";
            }
            context.newOutputMessage(AppConfig.DECLINED_ORDERS_TOPIC_NAME, AvroSchema.of(ValidatedFoodOrder.class))
                    .property("reason", message)
                    .value(order)
                    .sendAsync();
        }
    }
}

package sn.academy.food_delivery.services.processing;

import java.time.Instant;
import java.util.Random;
import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.config.AppConfig;
import sn.academy.food_delivery.models.avro.FoodOrder;
import sn.academy.food_delivery.models.avro.FoodOrderMeta;
import sn.academy.food_delivery.models.avro.OrderStatus;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;
import sn.academy.food_delivery.services.external.MockRestaurantOrderValidationService;

public class OrderSolicitationService implements Function<FoodOrder, Void> {
    private Logger logger;

    @Override
    public Void process(FoodOrder foodOrder, Context context) throws Exception {
        logger = context.getLogger();
        logger.info("Received order: " + foodOrder);
        // communicate with the restaurant to verify and give an ETA
        String orderKey = context.getCurrentRecord().getProperties().get("order-key");

        FoodOrderMeta foodOrderMeta =
                new FoodOrderMeta(
                        foodOrder.getMeta().getOrderId(),
                        foodOrder.getMeta().getCustomerId(),
                        foodOrder.getMeta().getTimePlaced(),
                        OrderStatus.ACCEPTED
                );

        // invoke the restaurants service
        Instant eta = MockRestaurantOrderValidationService
                .validateWithRestaurant(foodOrder.getDetails());

        if (eta == null) {
            foodOrderMeta.setOrderStatus(OrderStatus.DECLINED);
        }

        ValidatedFoodOrder validatedFoodOrder = ValidatedFoodOrder
                .newBuilder()
                .setDetails(foodOrder.getDetails())
                .setEta(eta)
                .setMeta(foodOrderMeta)
                .build();

        logger.info("Sending: " + validatedFoodOrder);
        context.newOutputMessage(AppConfig.ORDER_AGGREGATION_TOPIC_NAME, AvroSchema.of(ValidatedFoodOrder.class))
                .property("order-key", orderKey)
                .value(validatedFoodOrder)
                .sendAsync();
        return null;
    }
}

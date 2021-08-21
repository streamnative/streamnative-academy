package sn.academy.food_delivery.services.processing;

import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.config.AppConfig;
import sn.academy.food_delivery.models.avro.FoodOrder;
import sn.academy.food_delivery.models.avro.FoodOrderMeta;
import sn.academy.food_delivery.models.avro.OrderStatus;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;
import sn.academy.food_delivery.services.external.MockRestaurantCommunicationService;

public class RestaurantCommunicationService implements Function<FoodOrder, Void> {
    private Logger logger;
    private boolean isInitialized = false;

    @Override
    public Void process(FoodOrder foodOrder, Context context) throws Exception {
        if (!isInitialized) {
            init(context);
        }
        logger.info("Received order: " + foodOrder);
        // communicate with the restaurant to verify and give an ETA

        FoodOrderMeta foodOrderMeta =
                new FoodOrderMeta(
                        foodOrder.getMeta().getOrderId(),
                        foodOrder.getMeta().getCustomerId(),
                        foodOrder.getMeta().getTimePlaced(),
                        OrderStatus.ACCEPTED
                );

        // invoke the restaurants service
        String eta = MockRestaurantCommunicationService
                .validateWithRestaurant(foodOrder.getDetails());

        if (eta.isEmpty()) {
            foodOrderMeta.setOrderStatus(OrderStatus.DECLINED);
        }

        ValidatedFoodOrder validatedFoodOrder = new ValidatedFoodOrder();
        validatedFoodOrder.setDetails(foodOrder.getDetails());
        validatedFoodOrder.setRestaurantId(foodOrder.getRestaurantId());
        validatedFoodOrder.setEta(eta);
        validatedFoodOrder.setMeta(foodOrderMeta);

        logger.info("Sending: " + validatedFoodOrder);
        context.newOutputMessage(AppConfig.ORDER_AGGREGATION_TOPIC_NAME, AvroSchema.of(ValidatedFoodOrder.class))
                .properties(context.getCurrentRecord().getProperties())
                .value(validatedFoodOrder)
                .sendAsync();
        return null;
    }

    private void init(Context context) {
        logger = context.getLogger();
        isInitialized = true;
    }
}

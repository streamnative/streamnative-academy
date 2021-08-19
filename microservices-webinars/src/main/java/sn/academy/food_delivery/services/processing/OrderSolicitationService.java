package sn.academy.food_delivery.services.processing;

import java.time.Instant;
import java.util.Random;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.models.avro.FoodOrder;
import sn.academy.food_delivery.models.avro.FoodOrderMeta;
import sn.academy.food_delivery.models.avro.OrderStatus;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;
import sn.academy.food_delivery.services.external.MockRestaurantOrderValidationService;

public class OrderSolicitationService implements Function<FoodOrder, ValidatedFoodOrder> {
    private Logger logger;
    private Random random;
    @Override
    public ValidatedFoodOrder process(FoodOrder foodOrder, Context context) throws Exception {
        logger = context.getLogger();
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
        Instant eta = MockRestaurantOrderValidationService
                .validateWithRestaurant(foodOrder.getDetails());

        if (eta == null) {
            foodOrderMeta.setOrderStatus(OrderStatus.DECLINED);
        }

        return ValidatedFoodOrder
                .newBuilder()
                .setDetails(foodOrder.getDetails())
                .setEta(eta)
                .setMeta(foodOrderMeta)
                .build();
    }
}

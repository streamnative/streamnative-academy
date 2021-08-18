package sn.academy.food_delivery.services.validation;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.models.avro.FoodOrder;

public class OrderValidationService implements Function<FoodOrder, Void> {
    private Logger logger;

    @Override
    public Void process(FoodOrder foodOrder, Context context) throws Exception {
        logger = context.getLogger();
        logger.info("Received: " + foodOrder);
        return null;
    }
}

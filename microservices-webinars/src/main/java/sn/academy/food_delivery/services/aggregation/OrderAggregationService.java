package sn.academy.food_delivery.services.aggregation;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;

public class OrderAggregationService implements Function<ValidatedFoodOrder, Void> {
    private Logger logger;

    @Override
    public Void process(ValidatedFoodOrder validatedFoodOrder, Context context) throws Exception {
        logger = context.getLogger();
        logger.info(validatedFoodOrder.toString());
        return null;
    }
}

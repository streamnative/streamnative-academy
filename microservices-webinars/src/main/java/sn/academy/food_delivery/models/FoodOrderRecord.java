package sn.academy.food_delivery.models;

import java.util.Optional;
import org.apache.pulsar.functions.api.Record;
import sn.academy.food_delivery.models.avro.FoodOrder;

public class FoodOrderRecord implements Record<FoodOrder> {
    private final FoodOrder foodOrder;
    private final Long eventTime = System.currentTimeMillis();

    public FoodOrderRecord(FoodOrder food) {
        this.foodOrder = food;
    }

    @Override
    public FoodOrder getValue() {
        return foodOrder;
    }

    public Optional<Long> getEventTime() {
        return Optional.of(eventTime);
    }
}

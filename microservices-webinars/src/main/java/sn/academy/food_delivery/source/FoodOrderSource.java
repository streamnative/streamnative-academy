package sn.academy.food_delivery.source;

import java.util.Map;
import org.apache.pulsar.functions.api.Record;
import org.apache.pulsar.io.core.Source;
import org.apache.pulsar.io.core.SourceContext;
import sn.academy.food_delivery.models.FoodOrderRecord;
import sn.academy.food_delivery.models.avro.FoodOrder;

public class FoodOrderSource implements Source<FoodOrder> {
    private final DataGenerator<FoodOrder> generator = new FoodOrderGenerator();

    @Override
    public void open(Map<String, Object> config, SourceContext sourceContext) throws Exception {}

    @Override
    public Record<FoodOrder> read() throws Exception {
        Thread.sleep(500);
        FoodOrder food = generator.generate();
        System.out.println("Sending " + food);
        return new FoodOrderRecord(food);
    }

    @Override
    public void close() throws Exception {}
}

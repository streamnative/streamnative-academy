package sn.academy.food_delivery.source;

import java.util.Map;
import org.apache.pulsar.functions.api.Record;
import org.apache.pulsar.io.core.Source;
import org.apache.pulsar.io.core.SourceContext;
import org.slf4j.Logger;
import sn.academy.food_delivery.models.FoodOrderRecord;
import sn.academy.food_delivery.models.avro.FoodOrder;

public class FoodOrderSource implements Source<FoodOrder> {
    private final FoodOrderGenerator foodOrderGenerator = new FoodOrderGenerator();
    private  Logger logger;

    @Override
    public void open(Map<String, Object> config, SourceContext sourceContext) throws Exception {
        logger = sourceContext.getLogger();
    }

    @Override
    public Record<FoodOrder> read() throws Exception {
        Thread.sleep(500);
        FoodOrder food = foodOrderGenerator.generateOrder();
        logger.info(String.format("Sending Order: %s",food));
        return new FoodOrderRecord(food);
    }

    @Override
    public void close() throws Exception {}
}

package sn.academy.food_delivery.services.processing;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import sn.academy.food_delivery.models.avro.CreditCard;

/**
 * Demonstrates Content Cased Routing pattern
 *
 * @see https://www.enterpriseintegrationpatterns.com/patterns/messaging/ContentBasedRouter.html
 *
 */
public class PaymentService implements Function<CreditCard, Void> {
    @Override
    public Void process(CreditCard input, Context context) throws Exception {
        return null;
    }
}

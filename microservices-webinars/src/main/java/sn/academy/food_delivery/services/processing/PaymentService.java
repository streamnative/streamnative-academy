package sn.academy.food_delivery.services.processing;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.models.avro.ApplePay;
import sn.academy.food_delivery.models.avro.CreditCard;
import sn.academy.food_delivery.models.avro.PayPal;
import sn.academy.food_delivery.models.avro.Payment;

/**
 * Demonstrates Content Cased Routing pattern
 *
 * @see https://www.enterpriseintegrationpatterns.com/patterns/messaging/ContentBasedRouter.html
 *
 */
public class PaymentService implements Function<Payment, Void> {
    private Logger logger;

    @Override
    public Void process(Payment payment, Context context) throws Exception {
        logger = context.getLogger();
        logger.info("Received payment: " + payment);

        Class paymentType = payment.getMethodOfPayment().getType().getClass();
        Object paymentMethod = payment.getMethodOfPayment().getType();
        if (paymentType == ApplePay.class) {
            logger.info("ApplePay");
        } else if (paymentType == PayPal.class) {
            logger.info("Paypal");

        } else if (paymentType == CreditCard.class) {
            logger.info("CreditCard");

        }
        return null;
    }
}

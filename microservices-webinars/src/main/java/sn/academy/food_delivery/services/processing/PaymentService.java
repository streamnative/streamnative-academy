package sn.academy.food_delivery.services.processing;

import org.apache.pulsar.client.impl.schema.AvroSchema;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;
import org.slf4j.Logger;
import sn.academy.food_delivery.config.AppConfig;
import sn.academy.food_delivery.models.avro.ApplePay;
import sn.academy.food_delivery.models.avro.CreditCard;
import sn.academy.food_delivery.models.avro.PayPal;
import sn.academy.food_delivery.models.avro.Payment;
import sn.academy.food_delivery.models.avro.ValidatedFoodOrder;
import sn.academy.food_delivery.services.external.MockPaymentValidationService;

/**
 * Demonstrates Content Based Routing pattern
 *
 * @see https://www.enterpriseintegrationpatterns.com/patterns/messaging/ContentBasedRouter.html
 */
public class PaymentService implements Function<Payment, Void> {
    private Logger logger;

    @Override
    public Void process(Payment payment, Context context) throws Exception {
        logger = context.getLogger();
        logger.info("Received payment: " + payment);

        // based on the type of payment
        // create a mock http call to an external Open Banking API
        // to validate the payment information
        Class paymentType = payment.getMethodOfPayment().getType().getClass();
        boolean isPaymentValid = false;
        if (paymentType == ApplePay.class) {
            logger.info("Received Payment with ApplePay.");
            isPaymentValid = MockPaymentValidationService.validateApplePay((ApplePay) payment.getMethodOfPayment().getType(), payment.getAmount().getTotal());
        } else if (paymentType == PayPal.class) {
            logger.info("Received Payment with PayPal.");
            isPaymentValid = MockPaymentValidationService.validatePaypal((PayPal) payment.getMethodOfPayment().getType(), payment.getAmount().getTotal());
        } else if (paymentType == CreditCard.class) {
            logger.info("Received Payment with CreditCard.");
            isPaymentValid = MockPaymentValidationService.validateCreditCard((CreditCard) payment.getMethodOfPayment().getType(), payment.getAmount().getTotal());
        }
        payment.setIsAuthorized(isPaymentValid);
        ValidatedFoodOrder validatedFoodOrder = new ValidatedFoodOrder();
        validatedFoodOrder.setPayment(payment);

        logger.info("Sending: " + validatedFoodOrder);
        context.newOutputMessage(AppConfig.ORDER_AGGREGATION_TOPIC_NAME, AvroSchema.of(ValidatedFoodOrder.class))
                .properties(context.getCurrentRecord().getProperties())
                .value(validatedFoodOrder)
                .sendAsync();
        return null;
    }
}

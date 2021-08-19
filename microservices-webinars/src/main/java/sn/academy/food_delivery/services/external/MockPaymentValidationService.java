package sn.academy.food_delivery.services.external;

import sn.academy.food_delivery.models.avro.ApplePay;
import sn.academy.food_delivery.models.avro.CreditCard;
import sn.academy.food_delivery.models.avro.PayPal;

public class MockPaymentValidationService {
    /**
     * mock method that queries the paypal api to check
     * the validity of the provided payment method info and available funds
     * */
    public static boolean validatePaypal(PayPal payPal, float amount) {
        return true;
    }

    /**
     * mock method that queries the applepay api to check
     * the validity of the provided payment method info and available funds
     * */
    public static boolean validateApplePay(ApplePay applePay, float amount) {
        return true;
    }

    /**
     * mock method that queries the openbanking api to check
     * the validity of the provided payment method info and available funds
     * */
    public static boolean validateCreditCard(CreditCard creditCard, float amount) {
        return true;
    }
}

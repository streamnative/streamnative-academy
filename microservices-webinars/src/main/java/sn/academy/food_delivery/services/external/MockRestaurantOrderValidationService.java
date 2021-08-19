package sn.academy.food_delivery.services.external;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import sn.academy.food_delivery.models.avro.OrderDetail;

public class MockRestaurantOrderValidationService {
    private static final Random random = new Random();
    /***
     * Mock API call to communicate with the restaurant and validate the availability
     * and order supplies.
     * If accepted by the restaurant it returns an ETA.
     * */
    public static Instant validateWithRestaurant(List<OrderDetail> orderDetails) {
        double prob = random.nextDouble();
        if (prob < 0.2) {
            return null;
        }
        return Instant.now().plusSeconds(random.nextInt(30) + 30);
    }
}

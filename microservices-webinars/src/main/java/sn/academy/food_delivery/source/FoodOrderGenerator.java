package sn.academy.food_delivery.source;

import com.github.javafaker.Faker;
import com.github.javafaker.Food;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import sn.academy.food_delivery.models.avro.Address;
import sn.academy.food_delivery.models.avro.ApplePay;
import sn.academy.food_delivery.models.avro.CardType;
import sn.academy.food_delivery.models.avro.CreditCard;
import sn.academy.food_delivery.models.avro.FoodOrder;
import sn.academy.food_delivery.models.avro.FoodOrderMeta;
import sn.academy.food_delivery.models.avro.MenuItem;
import sn.academy.food_delivery.models.avro.OrderDetail;
import sn.academy.food_delivery.models.avro.OrderStatus;
import sn.academy.food_delivery.models.avro.PayPal;
import sn.academy.food_delivery.models.avro.Payment;
import sn.academy.food_delivery.models.avro.PaymentAmount;
import sn.academy.food_delivery.models.avro.PaymentMethod;

public class FoodOrderGenerator {
    private final int TOTAL_CUSTOMERS = 10000;
    private final int TOTAL_RESTAURANTS = 100;

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private final CardType[] cardTypes = CardType.values();

    private AtomicInteger orderId = new AtomicInteger();

    public FoodOrder generateOrder() {
        int totalMenuItems = random.nextInt(3) + 1;

        List<OrderDetail> orderDetails = IntStream.range(0, totalMenuItems).mapToObj(i -> {
            int quantity = random.nextInt(2) + 1;
            MenuItem menuItem = getMenuItem();
            float total = menuItem.getPrice() * quantity;
            return new OrderDetail(quantity, total * 0.1f + 10, menuItem);
        }).collect(Collectors.toList());

        float orderTotal = orderDetails.stream()
                .map(OrderDetail::getTotal)
                .reduce(0.0f, Float::sum);

        int customId = random.nextInt(TOTAL_CUSTOMERS);
        int restaurantId = random.nextInt(TOTAL_RESTAURANTS);
        String timePlaced = new Timestamp(System.currentTimeMillis()).toString();

        FoodOrderMeta foodOrderMeta =
                new FoodOrderMeta(orderId.getAndIncrement(), customId, timePlaced, OrderStatus.NEW);

        Payment payment = getPayment();
        PaymentAmount paymentAmount = PaymentAmount.newBuilder()
                .setFoodTotal(orderTotal)
                .setTax(0.24f)
                .setTotal(orderTotal + (orderTotal * 0.24f))
                .build();
        payment.setAmount(paymentAmount);
        return new FoodOrder(
                foodOrderMeta,
                restaurantId,
                orderDetails,
                getAddress(),
                payment,
                orderTotal);
    }

    private MenuItem getMenuItem() {
        Food food = faker.food();
        float price = Float.parseFloat(faker.commerce().price());
        return new MenuItem(random.nextInt(35), food.dish(), "", price);
    }

    private Payment getPayment() {
        CardType cardType = cardTypes[random.nextInt(cardTypes.length)];

        String card = faker.finance().creditCard();
        String ccv = (random.nextInt(900) + 100) + "";

        int month = random.nextInt(12) + 1;
        int year = random.nextInt(2022 + random.nextInt(10));
        CreditCard creditCard =
                new CreditCard(cardType, card, faker.address().zipCode(), ccv, month + "", year + "");

        PaymentMethod paymentMethod = null;
        switch (random.nextInt(3)) {
            case 0:
                paymentMethod = new PaymentMethod(new PayPal(card));
                break;
            case 1:
                paymentMethod = new PaymentMethod(creditCard);
                break;
            default:
                paymentMethod = new PaymentMethod(new ApplePay(card));
        }


        return new Payment(paymentMethod, new PaymentAmount(), false);
    }

    private Address getAddress() {
        com.github.javafaker.Address address = faker.address();
        return new Address(address.streetAddress(), address.city(), address.state(), address.country(), address.zipCode());
    }
}

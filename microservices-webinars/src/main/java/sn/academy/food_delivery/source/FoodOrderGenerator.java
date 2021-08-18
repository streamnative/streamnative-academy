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
import sn.academy.food_delivery.models.avro.CardType;
import sn.academy.food_delivery.models.avro.CreditCard;
import sn.academy.food_delivery.models.avro.FoodOrder;
import sn.academy.food_delivery.models.avro.MenuItem;
import sn.academy.food_delivery.models.avro.OrderDetail;
import sn.academy.food_delivery.models.avro.OrderStatus;

public class FoodOrderGenerator {
    private final int TOTAL_CUSTOMERS = 10000;
    private final int TOTAL_RESTAURANTS = 100;

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private final CardType[] cardTypes = CardType.values();
    private AtomicInteger orderId = new AtomicInteger();

    public FoodOrder generateOrder() {
        int totalMenuItems = random.nextInt(5) + 1;

        List<OrderDetail> orderDetails = IntStream.range(0, totalMenuItems).mapToObj(i -> {
            int quantity = random.nextInt(3) + 1;
            MenuItem menuItem = getMenuItem();
            float total = menuItem.getPrice() * quantity;
            return new OrderDetail(quantity, total, menuItem);
        }).collect(Collectors.toList());

        float orderTotal = orderDetails.stream()
                .map(OrderDetail::getTotal)
                .reduce(0.0f, Float::sum);

        int customId = random.nextInt(TOTAL_CUSTOMERS);
        int restaurantId = random.nextInt(TOTAL_RESTAURANTS);
        String timePlaced = new Timestamp(System.currentTimeMillis()).toString();

        return new FoodOrder(
                orderId.getAndIncrement(),
                customId,
                restaurantId,
                timePlaced,
                OrderStatus.NEW, orderDetails,
                getAddress(),
                getCreditCard(),
                orderTotal);
    }

    private MenuItem getMenuItem() {
        Food food = faker.food();
        float price = Float.parseFloat(faker.commerce().price());
        return new MenuItem(random.nextInt(35), food.dish(), "", price);
    }

    private CreditCard getCreditCard() {
        CardType cardType = cardTypes[random.nextInt(cardTypes.length)];
        String creditCard = faker.finance().creditCard();
        String ccv = (random.nextInt(900) + 100) + "";

        return new CreditCard(cardType, creditCard, faker.address().zipCode(), ccv);
    }

    private Address getAddress() {
        com.github.javafaker.Address address = faker.address();
        return new Address(address.streetAddress(), address.city(), address.state(), address.zipCode());
    }
}

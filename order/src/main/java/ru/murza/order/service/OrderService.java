package ru.murza.order.service;

import org.springframework.stereotype.Service;
import ru.murza.amqp.RabbitMQMessageProducer;
import ru.murza.feignclients.client.ClientClient;
import ru.murza.feignclients.client.RestaurantClient;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Dish;
import ru.murza.foodmodel.models.Order;
import ru.murza.foodmodel.models.Status;
import ru.murza.order.dto.OrderToSave;
import ru.murza.order.exception.StatusNotFoundException;
import ru.murza.order.exception.UserNotFoundException;
import ru.murza.order.repository.OrderRepository;

import java.util.*;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientClient clientClient;
    private final RestaurantClient restaurantClient;

    private final StatusService statusService;

    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public OrderService(OrderRepository orderRepository, ClientClient clientClient, RestaurantClient restaurantClient, StatusService statusService, RabbitMQMessageProducer rabbitMQMessageProducer) {
        this.orderRepository = orderRepository;
        this.clientClient = clientClient;
        this.restaurantClient = restaurantClient;
        this.statusService = statusService;
        this.rabbitMQMessageProducer = rabbitMQMessageProducer;
    }

    public Order saveOrder(Long clientId, OrderToSave orderToSave) throws UserNotFoundException, StatusNotFoundException {
        if (isUserPresent(clientId)){
            Status status = statusService.findByName("ACCEPTED");
            Basket basket = orderToSave.getBasket();
            Order order = Order.builder()
                    .basket_id(basket.getId())
                    .clientId(clientId)
                    //.storeId(basket.getStore().getId())
                    .order_date(new Date())
                    .table(orderToSave.getAddress())
                    .total_price(basket.getTotal_price())
                    .status(status)
                    .dishes(basket.getDishes().stream().map(Dish::getTitle).toList())
                    .build();
            //restaurantClient.delete(basket.getId());
            rabbitMQMessageProducer.publish(basket.getId(), "internal.exchange", "internal.notification.routing-key");
            return orderRepository.save(order);
        } else throw new UserNotFoundException("User not found");
    }

    public boolean isUserPresent(Long clientId){
        return clientClient.getClient(clientId).getBody().isPresent();
    }

    public List<Order> findAll(){
        return (List<Order>) orderRepository.findAll();
    }

    public HashMap<String, Integer> findPopularDishes(){
        List<Order> orders = (List<Order>) orderRepository.findAll();
        List<String> dishes = new ArrayList<>();
        orders.forEach(order -> dishes.addAll(order.getDishes()));

        HashMap <String, Integer> dishCounter = new HashMap<>();
        dishes.forEach(dish -> {
            if (dishCounter.containsKey(dish)){
                dishCounter.put(dish, dishCounter.get(dish) + 1);
            } else
                dishCounter.put(dish, 1);
        }
        );

        System.out.println(dishCounter);
        System.out.println("=================================");
        System.out.println("=================================");
        System.out.println("=================================");
        System.out.println("=================================");
        System.out.println("=================================");
        System.out.println("=================================");
        return dishCounter;
    }

}

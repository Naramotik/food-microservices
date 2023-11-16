package ru.murza.order.service;

import org.springframework.stereotype.Service;
import ru.murza.feignclients.client.ClientClient;
import ru.murza.feignclients.client.RestaurantClient;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Dish;
import ru.murza.foodmodel.models.Order;
import ru.murza.foodmodel.models.Status;
import ru.murza.order.dto.OrderToSave;
import ru.murza.order.repository.OrderRepository;

import java.util.Date;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ClientClient clientClient;
    private final RestaurantClient restaurantClient;
    private final StatusService statusService;

    public OrderService(OrderRepository orderRepository, ClientClient clientClient, RestaurantClient restaurantClient, StatusService statusService) {
        this.orderRepository = orderRepository;
        this.clientClient = clientClient;
        this.restaurantClient = restaurantClient;
        this.statusService = statusService;
    }

    public Order saveOrder(Long clientId, OrderToSave orderToSave){
        if (isUserPresent(clientId)){
            Status status = statusService.findByName("ACCEPTED");
            Basket basket = orderToSave.getBasket();
            Order order = Order.builder()
                    .basket_id(basket.getId())
                    .clientId(clientId)
                    //.storeId(basket.getStore().getId())
                    .order_date(new Date())
                    .address(orderToSave.getAddress())
                    .total_price(basket.getTotal_price())
                    .status(status)
                    .dishes(basket.getDishes().stream().map(Dish::getTitle).toList())
                    .build();
            restaurantClient.delete(basket.getId());
            return orderRepository.save(order);
        } else return null;
    }

    public boolean isUserPresent(Long clientId){
        return clientClient.getClient(clientId).getBody().isPresent();
    }

}

package ru.murza.restaurant.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.murza.restaurant.service.BasketService;

@Component
public class NotificationConsumer {
    private final BasketService basketService;

    public NotificationConsumer(BasketService basketService) {
        this.basketService = basketService;
    }
    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(Long basket_id){
        basketService.deleteById(basket_id);
    }
}

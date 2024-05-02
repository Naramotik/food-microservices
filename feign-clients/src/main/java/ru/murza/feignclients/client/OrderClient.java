package ru.murza.feignclients.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "order")
public interface OrderClient {

}

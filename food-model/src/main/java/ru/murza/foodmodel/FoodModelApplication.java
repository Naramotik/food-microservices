package ru.murza.foodmodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })
public class FoodModelApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodModelApplication.class, args);
    }
}

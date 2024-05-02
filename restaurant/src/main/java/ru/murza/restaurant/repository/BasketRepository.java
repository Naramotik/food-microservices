package ru.murza.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.murza.foodmodel.enums.BasketStatus;
import ru.murza.foodmodel.enums.DishCategory;
import ru.murza.foodmodel.models.Basket;
import ru.murza.foodmodel.models.Dish;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BasketRepository extends CrudRepository<Basket, Long> {
    Optional<Basket> findByClientId(Long client_id);

    @Transactional
    @Query("from Basket basket where basketStatus = :basketStatus")
    List<Basket> findByBasketStatus(@Param("basketStatus") BasketStatus basketStatus);

    @Transactional
    @Query("from Basket basket where basketStatus = :basketStatus and clientId = :clientId")
    Optional<Basket> findByBasketStatusAndClient_id(@Param("basketStatus") BasketStatus basketStatus,
                                                @Param("clientId") Long clientId);

    @Transactional
    @Query("from Basket basket where basketStatus = :basketStatus and acceptance_date between :from AND :to")
    List<Basket> findByBasketStatusAndBetweenDates(@Param("basketStatus") BasketStatus basketStatus,
                                    @Param("from") Date from,
                                    @Param("to") Date to);
}

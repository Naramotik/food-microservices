package ru.murza.client.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Client;

import java.util.Optional;


@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
    Optional<Client> findByNumber(String number);
}

package ru.murza.client.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.murza.foodmodel.models.Client;
import ru.murza.foodmodel.models.Schedule;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
}

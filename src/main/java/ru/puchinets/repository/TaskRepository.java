package ru.puchinets.repository;

import org.springframework.stereotype.Repository;
import ru.puchinets.entity.Task;
import ru.puchinets.entity.User;

import java.util.List;

@Repository
public interface TaskRepository extends BaseRepository<Task, Long> {

    List<Task> findAllByUser(User user);

    List<Task> findAllByCreatedBy(User user);
}

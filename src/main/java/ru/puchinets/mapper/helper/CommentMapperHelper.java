package ru.puchinets.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.puchinets.entity.Task;
import ru.puchinets.entity.User;
import ru.puchinets.repository.TaskRepository;
import ru.puchinets.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentMapperHelper {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public User idToUser(Long userId) {
        Optional<User> mayBeUser = userRepository.findById(userId);
        return mayBeUser.orElse(null);
    }

    public Task idToTask(Long taskId) {
        Optional<Task> mayBeTask = taskRepository.findById(taskId);
        return mayBeTask.orElse(null);
    }

}

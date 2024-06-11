package ru.puchinets.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.puchinets.entity.Tag;
import ru.puchinets.entity.User;
import ru.puchinets.enums.TaskStatus;
import ru.puchinets.repository.TagRepository;
import ru.puchinets.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class TaskMapperHelper {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;

    public String toDto(TaskStatus taskStatus) {
        return taskStatus.name;
    }

    public TaskStatus fromDto(String taskStatus) {
        return TaskStatus.byName(taskStatus);
    }

    public User userFromDto(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public Tag tagFromDto(Long tagId) {
        return tagRepository.findById(tagId).orElse(null);
    }
}

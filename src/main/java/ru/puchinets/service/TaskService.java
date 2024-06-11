package ru.puchinets.service;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.puchinets.dto.request.TaskRequestDto;
import ru.puchinets.dto.response.TaskResponseDto;
import ru.puchinets.entity.Task;
import ru.puchinets.entity.User;
import ru.puchinets.exception.BadRequestException;
import ru.puchinets.mapper.TaskMapper;
import ru.puchinets.repository.TaskRepository;
import ru.puchinets.repository.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
public class TaskService extends AbstractService<TaskRequestDto, TaskResponseDto, Task, Long> {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        super(taskRepository, taskMapper);
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    public List<TaskResponseDto> getAllTasksForUser(Long userId) {
        Optional<User> mayBeUser = userRepository.findById(userId);
        if (mayBeUser.isEmpty()) throw new BadRequestException("Incorrect user ID");
        return taskRepository.findAllByUser(mayBeUser.get())
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public List<TaskResponseDto> getAllCreatedTasksForUser(Long userId) {
        Optional<User> mayBeUser = userRepository.findById(userId);
        if (mayBeUser.isEmpty()) throw new BadRequestException("Incorrect user ID");
        return taskRepository.findAllByCreatedBy(mayBeUser.get())
                .stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Transactional
    public TaskResponseDto createByAuthor(TaskRequestDto request, Long authorId) {
        if (request.userId() == null || authorId == null) throw new BadRequestException("Incorrect request parameters");
        Task task = taskMapper.fromDto(request);
        Optional<User> mayBeUser = userRepository.findById(request.userId());
        Optional<User> mayBeAuthor = userRepository.findById(authorId);
        if (mayBeUser.isEmpty() || mayBeAuthor.isEmpty()) throw new BadRequestException("Incorrect request parameters");
        task.setUser(mayBeUser.get());
        task.setCreatedBy(mayBeAuthor.get());
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional
    public Optional<TaskResponseDto> update(@NonNull Long authorId, @NonNull Long taskId, @NonNull TaskRequestDto request) {
        Optional<Task> mayBeTask = taskRepository.findById(taskId);
        if (mayBeTask.isEmpty() || !authorId.equals(mayBeTask.get().getCreatedBy().getId()))
            throw new BadRequestException("Incorrect request parameters");
        Task task = taskMapper.fromDto(request, mayBeTask.get());
        return Optional.of(taskRepository.saveAndFlush(task)).map(taskMapper::toDto);
    }
}

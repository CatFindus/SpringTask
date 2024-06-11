package ru.puchinets.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.puchinets.dto.request.TaskRequestDto;
import ru.puchinets.dto.response.TaskResponseDto;
import ru.puchinets.dto.response.UserResponseDto;
import ru.puchinets.entity.Company;
import ru.puchinets.entity.Tag;
import ru.puchinets.entity.Task;
import ru.puchinets.entity.User;
import ru.puchinets.enums.TaskStatus;
import ru.puchinets.exception.BadRequestException;
import ru.puchinets.mapper.TaskMapper;
import ru.puchinets.repository.TaskRepository;
import ru.puchinets.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskMapper taskMapper;

    private final User user = new User(1L, "un", "fn", "ln", "pass", LocalDate.now(), new Company(), null, null);
    private final Task task = new Task(1L, "description", "content", user, user, TaskStatus.ARCHIVED, new Tag());
    private final TaskResponseDto response = new TaskResponseDto(1L,
            "description",
            "content",
            new UserResponseDto(1L, "un", "fn", "ln", "pass", null, null),
            LocalDateTime.now().toString(),
            LocalDateTime.now().toString(),
            TaskStatus.FINISHED.name,
            null, null);
    private final TaskRequestDto newRequest = new TaskRequestDto(null, "description", "content", 1L, TaskStatus.ARCHIVED.name, 1L, null, null);
    private final TaskRequestDto badRequest = new TaskRequestDto(null, "description", "content", null, TaskStatus.ARCHIVED.name, 1L, null, null);
    private final TaskRequestDto badRequest2 = new TaskRequestDto(null, "description", "content", 2L, TaskStatus.ARCHIVED.name, 1L, null, null);
    @Test
    void getAllTasksForUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        when(taskRepository.findAllByUser(user)).thenReturn(List.of(task));
        when(taskMapper.toDto(task)).thenReturn(response);
        List<TaskResponseDto> allTasksForUser = taskService.getAllTasksForUser(1L);
        assertNotNull(allTasksForUser);
        assertEquals(1, allTasksForUser.size());
        assertEquals(response, allTasksForUser.get(0));
        assertThrows(BadRequestException.class, () -> taskService.getAllTasksForUser(2L));
    }

    @Test
    void getAllCreatedTasksForUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        when(taskRepository.findAllByCreatedBy(user)).thenReturn(List.of(task));
        when(taskMapper.toDto(task)).thenReturn(response);
        List<TaskResponseDto> allTasksForUser = taskService.getAllCreatedTasksForUser(1L);
        assertNotNull(allTasksForUser);
        assertEquals(1, allTasksForUser.size());
        assertEquals(response, allTasksForUser.get(0));
        assertThrows(BadRequestException.class, () -> taskService.getAllCreatedTasksForUser(2L));
    }

    @Test
    void createByAuthor() {
        when(taskMapper.fromDto(newRequest)).thenReturn(task);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(response);
        TaskResponseDto byAuthor = taskService.createByAuthor(newRequest, 1L);
        assertNotNull(byAuthor);
        assertEquals(response, byAuthor);
        assertThrows(BadRequestException.class, () -> taskService.createByAuthor(newRequest, null));
        assertThrows(BadRequestException.class, () -> taskService.createByAuthor(badRequest, 1L));
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> taskService.createByAuthor(newRequest, 2L));
        assertThrows(BadRequestException.class, () -> taskService.createByAuthor(badRequest2, 1L));
    }

    @Test
    void update() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.fromDto(newRequest, task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(response);
        when(taskRepository.saveAndFlush(task)).thenReturn(task);
        Optional<TaskResponseDto> update = taskService.update(1L, 1L, newRequest);
        assertTrue(update.isPresent());
        assertEquals(task.getId(), update.get().id());
        assertThrows(BadRequestException.class, () -> taskService.update(2L, 1L, newRequest));
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class, () -> taskService.update(1L, 1L, newRequest));
    }
}
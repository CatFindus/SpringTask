package ru.puchinets.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.puchinets.annotation.IT;
import ru.puchinets.dto.request.TaskRequestDto;
import ru.puchinets.dto.response.TaskResponseDto;
import ru.puchinets.enums.TaskStatus;
import ru.puchinets.service.TaskService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
class TaskServiceTestIT {

    @Autowired
    private TaskService taskService;



    @Test
    void getAllTasksForUser() {
        List<TaskResponseDto> allTasksForUser = taskService.getAllTasksForUser(1L);
        assertNotNull(allTasksForUser);
        assertEquals(3, allTasksForUser.size());
    }

    @Test
    void getAllCreatedTasksForUser() {
        List<TaskResponseDto> allCreatedTasksForUser = taskService.getAllCreatedTasksForUser(1L);
        assertNotNull(allCreatedTasksForUser);
        assertEquals(1, allCreatedTasksForUser.size());
    }

    @Test
    void createByAuthor() {
        TaskRequestDto requestDto = new TaskRequestDto(null, "description", "content", 1L, TaskStatus.IN_PROGRESS.name, 1L, null, null);
        TaskResponseDto byAuthor = taskService.createByAuthor(requestDto, 1L);
        assertNotNull(byAuthor);
        assertNotNull(byAuthor.id());
        List<TaskResponseDto> allCreatedTasksForUser = taskService.getAllCreatedTasksForUser(1L);
        assertEquals(2, allCreatedTasksForUser.size());
    }

    @Test
    void update() {
        TaskRequestDto requestDto = new TaskRequestDto(1L, "description", "content", 1L, TaskStatus.IN_PROGRESS.name, 1L, null, null);
        Optional<TaskResponseDto> update = taskService.update(1L, 1L, requestDto);
        assertTrue(update.isPresent());
        assertEquals("description", update.get().description());
        List<TaskResponseDto> allCreatedTasksForUser = taskService.getAllCreatedTasksForUser(1L);
        assertEquals(1 , allCreatedTasksForUser.size());
    }
}
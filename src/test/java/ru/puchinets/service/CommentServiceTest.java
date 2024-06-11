package ru.puchinets.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.puchinets.dto.response.CommentResponseDto;
import ru.puchinets.dto.response.UserResponseDto;
import ru.puchinets.entity.Comment;
import ru.puchinets.entity.Task;
import ru.puchinets.entity.User;
import ru.puchinets.mapper.CommentMapper;
import ru.puchinets.repository.CommentRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;
    @Mock
    CommentMapper commentMapper;
    @Mock
    CommentRepository commentRepository;
    private final Comment comment = new Comment("subject", "content", new User(), new Task());
    private final CommentResponseDto response = new CommentResponseDto(1L, "subject", "content", "created",
            new UserResponseDto(1L, "un", "fn", "ln", "pass", LocalDate.now(), null));
    @Test
    void findAllByTask() {
        when(commentRepository.findAllByTaskIdOrderByCreatedAtDesc(1L)).thenReturn(List.of(comment));
        when(commentMapper.toDto(comment)).thenReturn(response);
        List<CommentResponseDto> allByTask = commentService.findAllByTask(1L);
        assertNotNull(allByTask);
        assertFalse(allByTask.isEmpty());
        assertEquals(1, allByTask.size());
        assertEquals(response, allByTask.get(0));
    }
}
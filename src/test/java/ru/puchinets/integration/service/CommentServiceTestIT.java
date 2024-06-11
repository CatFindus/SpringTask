package ru.puchinets.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.puchinets.annotation.IT;
import ru.puchinets.dto.response.CommentResponseDto;
import ru.puchinets.service.CommentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@IT
class CommentServiceTestIT {

    @Autowired
    CommentService commentService;

    @Test
    void findAllByTask() {
        List<CommentResponseDto> allByTask = commentService.findAllByTask(1L);
        assertNotNull(allByTask);
        assertEquals(3, allByTask.size());
        List<CommentResponseDto> emptyCommentList = commentService.findAllByTask(1000L);
        assertNotNull(emptyCommentList);
        assertTrue(emptyCommentList.isEmpty());
    }
}
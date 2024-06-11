package ru.puchinets.service;

import org.springframework.stereotype.Service;
import ru.puchinets.dto.request.CommentRequestDto;
import ru.puchinets.dto.response.CommentResponseDto;
import ru.puchinets.entity.Comment;
import ru.puchinets.mapper.CommentMapper;
import ru.puchinets.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService extends AbstractService<CommentRequestDto, CommentResponseDto, Comment, Long> {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentService(CommentRepository commentRepository, CommentMapper commentMapper) {
        super(commentRepository, commentMapper);
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    public List<CommentResponseDto> findAllByTask(Long taskId) {
        return commentRepository.findAllByTaskIdOrderByCreatedAtDesc(taskId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }
}

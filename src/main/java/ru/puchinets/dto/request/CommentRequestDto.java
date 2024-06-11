package ru.puchinets.dto.request;


public record CommentRequestDto(Long id,
                                String subject,
                                String content,
                                Long userId,
                                Long taskId) {

}

package ru.puchinets.dto.response;

public record CommentResponseDto(Long id,
                                 String subject,
                                 String content,
                                 String createdAt,
                                 UserResponseDto user) {
}

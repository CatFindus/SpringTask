package ru.puchinets.dto.response;


import java.time.LocalDateTime;

public record TagResponseDto(Long id,
                             String name,
                             String color,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt,
                             String type) {
}

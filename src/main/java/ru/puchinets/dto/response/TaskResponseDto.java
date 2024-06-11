package ru.puchinets.dto.response;


public record TaskResponseDto(Long id,
                              String description,
                              String content,
                              UserResponseDto user,
                              String createdAt,
                              String updatedAt,
                              String taskStatus,
                              TagResponseDto tag,
                              UserResponseDto createdBy) {
}

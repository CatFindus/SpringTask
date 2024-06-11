package ru.puchinets.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TaskRequestDto(Long id,
                             @NotEmpty @Size(min = 3, max = 64, message = "Description between 3 and 64 characters") String description,
                             @NotEmpty String content,
                             @Positive Long userId,
                             String taskStatus,
                             Long tagId,
                             Integer page,
                             Integer size) {
}

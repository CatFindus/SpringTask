package ru.puchinets.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CompanyRequestDto(Integer id,
                                @NotEmpty @Size(min = 3, max = 64, message = "Company name between 3 and 64 characters") String name) {
}

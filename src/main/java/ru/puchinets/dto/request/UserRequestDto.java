package ru.puchinets.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRequestDto(Long id,
                             @NotEmpty @Size(min = 3, max = 64, message = "UserName between 3 and 64 characters") String userName,
                             @Size(min = 3, max = 64, message = "Firstname between 3 and 64 characters") String firstname,
                             @Size(min = 3, max = 64, message = "Lastname between 3 and 64 characters") String lastname,
                             @NotEmpty String password,
                             @NotNull LocalDate birthDate,
                             @NotNull Integer companyId,
                             Integer page,
                             Integer size) {
}

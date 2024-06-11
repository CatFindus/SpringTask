package ru.puchinets.dto.response;

import java.time.LocalDate;

public record UserResponseDto(Long id,
                              String userName,
                              String firstname,
                              String lastname,
                              String password,
                              LocalDate birthDate,
                              CompanyResponseDto company) {
}

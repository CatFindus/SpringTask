package ru.puchinets.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.puchinets.annotation.IT;
import ru.puchinets.dto.request.UserRequestDto;
import ru.puchinets.dto.response.UserResponseDto;
import ru.puchinets.exception.BadRequestException;
import ru.puchinets.service.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
class UserServiceTestIT {

    @Autowired
    private UserService userService;

    @Test
    void create() {
        UserRequestDto requestDto = new UserRequestDto(null, "someUserName", "fn", "ln", "pass", LocalDate.now(), 1, null, null);
        UserRequestDto badDto1 = new UserRequestDto(null, "someUserName", "fn", "ln", "pass", LocalDate.now(), 1000, null, null);
        UserRequestDto badDto2 = new UserRequestDto(null, "someUserName", "fn", "ln", "pass", LocalDate.now(),null, null, null);
        UserResponseDto userResponseDto = userService.create(requestDto);
        assertNotNull(userResponseDto);
        assertNotNull(userResponseDto.id());
        assertThrows(BadRequestException.class, ()-> userService.create(badDto1));
        assertThrows(BadRequestException.class, ()-> userService.create(badDto2));
    }

    @Test
    void findUsersByCompanyId() {
        List<UserResponseDto> usersByCompanyId = userService.findUsersByCompanyId(1);
        assertEquals(3, usersByCompanyId.size());
        List<UserResponseDto> usersByCompanyId1 = userService.findUsersByCompanyId(1000);
        assertNotNull(usersByCompanyId1);
        assertTrue(usersByCompanyId1.isEmpty());
    }
}
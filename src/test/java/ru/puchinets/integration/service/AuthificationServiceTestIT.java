package ru.puchinets.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.puchinets.annotation.IT;
import ru.puchinets.dto.authorization.Authification;
import ru.puchinets.dto.authorization.LoginDto;
import ru.puchinets.dto.response.UserResponseDto;
import ru.puchinets.service.AuthificationService;
import ru.puchinets.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
@IT
class AuthificationServiceTestIT {

    @Autowired
    AuthificationService service;

    @Autowired
    UserService userService;

    @Test
    void login() {
        List<UserResponseDto> all = userService.findAll();
        LoginDto goodLogin = new LoginDto(all.get(0).userName(), all.get(0).password());
        LoginDto badLogin = new LoginDto("badUn", "badPass");
        Authification logged = service.login(goodLogin);
        Authification unlogged = service.login(badLogin);
        assertTrue(logged.authificated());
        assertFalse(unlogged.authificated());
    }
}
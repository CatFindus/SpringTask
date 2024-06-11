package ru.puchinets.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.puchinets.dto.authorization.Authification;
import ru.puchinets.dto.authorization.LoginDto;
import ru.puchinets.entity.Company;
import ru.puchinets.entity.User;
import ru.puchinets.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthificationServiceTest {

    @InjectMocks
    AuthificationService authificationService;
    @Mock
    UserRepository userRepository;
    private final LoginDto badDto = new LoginDto(null, "somepaertss");
    private final LoginDto goodDto = new LoginDto("someLogin", "somePass");
    private final Company company = new Company(1, "name", null, null);
    private final User user = new User(1L, "someLogin", "fn", "ln", "somePass", null,
            company, null, null);
    @Test
    void login() {
        when(userRepository.findUserByUserName("someLogin")).thenReturn(Optional.of(user));
        Authification login = authificationService.login(goodDto);
        assertTrue(login.authificated());
        assertEquals(user.getId(), login.id());
        assertEquals(company.getId(), login.companyId());
        Authification unLogged = authificationService.login(badDto);
        assertFalse(unLogged.authificated());
        assertNull(unLogged.id());
        assertNull(unLogged.companyId());
    }
}
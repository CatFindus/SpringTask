package ru.puchinets.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.puchinets.dto.request.UserRequestDto;
import ru.puchinets.dto.response.UserResponseDto;
import ru.puchinets.entity.Company;
import ru.puchinets.entity.User;
import ru.puchinets.exception.BadRequestException;
import ru.puchinets.mapper.UserMapper;
import ru.puchinets.repository.CompanyRepository;
import ru.puchinets.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)

class UserServiceTest {


    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Spy
    private UserMapper userMapper;
    private final Company company = new Company(1, "TestCompany", null, null);
    private final User user = new User(1L, "testUN", "testFN", "testLN", "testPass", LocalDate.now(), company, null, null);
    private final User user_without_id = new User(null, "testUN", "testFN", "testLN", "testPass", user.getBirthDate(), company, null, null);
    private final UserRequestDto requestDto = new UserRequestDto(null, user.getUserName(), user.getFirstname(), user.getLastname(), user.getPassword(), user.getBirthDate(), user.getCompany().getId(), null, null);
    private final UserRequestDto requestDto2 = new UserRequestDto(null, user.getUserName(), user.getFirstname(), user.getLastname(), user.getPassword(), user.getBirthDate(), null, null, null);
    @Test
    void findUsersByCompanyId() {
        when(userRepository.findUsersByCompanyId(1)).thenReturn(List.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserResponseDto(1L, null,null,null,null, null, null));
        List<UserResponseDto> usersByCompanyId = userService.findUsersByCompanyId(1);
        assertNotNull(usersByCompanyId);
        assertEquals(1, usersByCompanyId.size());
        assertEquals(user.getId(),usersByCompanyId.get(0).id());
        verify(userMapper, times(1)).toDto(user);
    }

    @Test
    void create_sucsefull() {
        when(companyRepository.findById(1)).thenReturn(Optional.of(company));
        when(userRepository.save(Mockito.any())).thenReturn(user);
        when(userMapper.fromDto(requestDto)).thenReturn(user_without_id);
        when(userMapper.toDto(user)).thenReturn(new UserResponseDto(user.getId(),  user.getUserName(), user.getFirstname(), user.getLastname(), user.getPassword(), user.getBirthDate(), null));
        UserResponseDto userResponseDto = userService.create(requestDto);
        assertEquals(user.getId(), userResponseDto.id());
    }

    @Test
    void create_unsucsefull() {
        assertThrows(BadRequestException.class,
                () -> userService.create(requestDto2));
        when(companyRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(BadRequestException.class,
                () -> userService.create(requestDto));
    }
}
package ru.puchinets.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.puchinets.dto.authorization.Authification;
import ru.puchinets.dto.authorization.LoginDto;
import ru.puchinets.entity.Company;
import ru.puchinets.entity.User;
import ru.puchinets.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthificationService {

    private final UserRepository userRepository;

    public Authification login(LoginDto dto) {
        if (dto == null || dto.userName() == null) return new Authification(null, false, null);
        Optional<User> mayBeUser = userRepository.findUserByUserName(dto.userName());
        return new Authification(mayBeUser.map(User::getId).orElse(null),
                dto.password() != null && dto.password().equals(mayBeUser.map(User::getPassword).orElse(null)),
                mayBeUser.map(User::getCompany).map(Company::getId).orElse(null));
    }
}

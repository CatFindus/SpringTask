package ru.puchinets.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.puchinets.dto.request.UserRequestDto;
import ru.puchinets.dto.response.UserResponseDto;
import ru.puchinets.entity.Company;
import ru.puchinets.entity.User;
import ru.puchinets.exception.BadRequestException;
import ru.puchinets.mapper.UserMapper;
import ru.puchinets.repository.CompanyRepository;
import ru.puchinets.repository.UserRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class UserService extends AbstractService<UserRequestDto, UserResponseDto, User, Long> {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, UserMapper userMapper, CompanyRepository companyRepository) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.companyRepository = companyRepository;
    }

    @Transactional
    @Override
    public UserResponseDto create(UserRequestDto request) {
        if (request.companyId() == null) throw new BadRequestException("Incorrect request parameters");
        User user = userMapper.fromDto(request);
        Optional<Company> mayBeCompany = companyRepository.findById(request.companyId());
        if (mayBeCompany.isEmpty()) throw new BadRequestException("Incorrect request parameters");
        user.setCompany(mayBeCompany.get());
        return userMapper.toDto(userRepository.save(user));
    }

    public List<UserResponseDto> findUsersByCompanyId(Integer companyId) {
        return userRepository
                .findUsersByCompanyId(companyId)
                .stream()
                .map(userMapper::toDto).toList();
    }
}

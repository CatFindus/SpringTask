package ru.puchinets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.puchinets.dto.request.UserRequestDto;
import ru.puchinets.dto.response.UserResponseDto;
import ru.puchinets.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserRequestDto, UserResponseDto, User> {

    @Override
    UserResponseDto toDto(User user);

    @Override
    User fromDto(UserRequestDto dto);

    default User fromDto(UserRequestDto dto, User user) {
        if (dto.userName() != null) user.setUserName(dto.userName());
        if (dto.firstname() != null) user.setFirstname(dto.firstname());
        if (dto.lastname() != null) user.setLastname(dto.lastname());
        if (dto.birthDate() != null) user.setBirthDate(dto.birthDate());
        if (dto.password() != null) user.setPassword(dto.password());
        return user;
    }

}

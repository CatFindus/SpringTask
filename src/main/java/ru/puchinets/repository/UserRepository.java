package ru.puchinets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.puchinets.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long>, JpaRepository<User, Long> {
    Optional<User> findUserByUserName(String user);

    List<User> findUsersByCompanyId(Integer companyId);
}

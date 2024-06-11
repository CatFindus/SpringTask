package ru.puchinets.repository;

import org.springframework.stereotype.Repository;
import ru.puchinets.entity.Tag;

import java.util.List;

@Repository
public interface TagRepository extends BaseRepository<Tag, Long> {
    List<Tag> findAllByCompanyId(Integer companyId);
}

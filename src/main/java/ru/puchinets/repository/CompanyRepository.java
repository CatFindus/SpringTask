package ru.puchinets.repository;

import org.springframework.stereotype.Repository;
import ru.puchinets.entity.Company;

@Repository
public interface CompanyRepository extends BaseRepository<Company, Integer> {
}

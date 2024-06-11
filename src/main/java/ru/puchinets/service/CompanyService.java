package ru.puchinets.service;

import org.springframework.stereotype.Service;
import ru.puchinets.dto.request.CompanyRequestDto;
import ru.puchinets.dto.response.CompanyResponseDto;
import ru.puchinets.entity.Company;
import ru.puchinets.mapper.CompanyMapper;
import ru.puchinets.repository.CompanyRepository;

@Service
public class CompanyService extends AbstractService<CompanyRequestDto, CompanyResponseDto, Company, Integer> {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, CompanyMapper companyMapper) {
        super(companyRepository, companyMapper);
        this.companyRepository = companyRepository;
        this.companyMapper = companyMapper;
    }
}

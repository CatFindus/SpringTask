package ru.puchinets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.puchinets.dto.request.CompanyRequestDto;
import ru.puchinets.dto.response.CompanyResponseDto;
import ru.puchinets.entity.Company;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompanyMapper extends BaseMapper<CompanyRequestDto, CompanyResponseDto, Company> {
    @Override
    CompanyResponseDto toDto(Company company);

    @Override
    Company fromDto(CompanyRequestDto dto);

    @Override
    default Company fromDto(CompanyRequestDto dto, Company company) {
        if (dto.name() != null) company.setName(dto.name());
        return company;
    }
}

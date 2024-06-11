package ru.puchinets.mapper.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.puchinets.entity.Company;
import ru.puchinets.enums.TagType;
import ru.puchinets.repository.CompanyRepository;

@Component
@RequiredArgsConstructor
public class TagMapperHelper {

    private final CompanyRepository companyRepository;

    public String toDto(TagType type) {
        return type.getName();
    }

    public TagType fromDto(String type) {
        return TagType.byName(type);
    }

    public Company fromDto(Integer companyId) {
        if (companyId == null) return null;
        return companyRepository.getById(companyId);
    }
}

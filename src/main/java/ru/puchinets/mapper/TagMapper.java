package ru.puchinets.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.puchinets.dto.request.TagRequestDto;
import ru.puchinets.dto.response.TagResponseDto;
import ru.puchinets.entity.Tag;
import ru.puchinets.mapper.helper.TagMapperHelper;

@Mapper(componentModel = "spring", uses = {TagMapperHelper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper extends BaseMapper<TagRequestDto, TagResponseDto, Tag> {

    @Override
    TagResponseDto toDto(Tag entity);

    @Override
    @Mapping(source = "companyId", target = "company")
    @Mapping(target = "id", ignore = true)
    Tag fromDto(TagRequestDto request);

    @Override
    default Tag fromDto(TagRequestDto request, Tag entity) {
        Tag reqTag = fromDto(request);
        if (request.id() == null || !request.id().equals(entity.getId())) return entity;
        if (reqTag.getName() != null) entity.setName(reqTag.getName());
        if (reqTag.getColor() != null) entity.setColor(reqTag.getColor());
        if (reqTag.getType() != null) entity.setType(reqTag.getType());
        return entity;
    }
}

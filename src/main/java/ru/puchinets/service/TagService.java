package ru.puchinets.service;

import org.springframework.stereotype.Service;
import ru.puchinets.dto.request.TagRequestDto;
import ru.puchinets.dto.response.TagResponseDto;
import ru.puchinets.entity.Tag;
import ru.puchinets.mapper.TagMapper;
import ru.puchinets.repository.TagRepository;

import java.util.List;

@Service
public class TagService extends AbstractService<TagRequestDto, TagResponseDto, Tag, Long> {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        super(tagRepository, tagMapper);
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public List<TagResponseDto> findAllByCompanyId(Integer companyId) {
        return tagRepository
                .findAllByCompanyId(companyId)
                .stream()
                .map(tagMapper::toDto)
                .toList();
    }
}

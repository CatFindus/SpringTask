package ru.puchinets.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.puchinets.dto.response.TagResponseDto;
import ru.puchinets.entity.Company;
import ru.puchinets.entity.Tag;
import ru.puchinets.enums.TagType;
import ru.puchinets.mapper.TagMapper;
import ru.puchinets.mapper.TagMapperImpl;
import ru.puchinets.mapper.helper.TagMapperHelper;
import ru.puchinets.repository.CompanyRepository;
import ru.puchinets.repository.TagRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks
    private TagService tagService;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private TagMapper tagMapper;
    private final Tag tag = new Tag("name", "color", TagType.SECURITY, new Company());
    private final TagResponseDto responseDto = new TagResponseDto(1L, "name", "color", LocalDateTime.now(), LocalDateTime.now(), TagType.SECURITY.getName());
    @Test
    void findAllByCompanyId() {
        when(tagRepository.findAllByCompanyId(1)).thenReturn(List.of(tag));
        when(tagMapper.toDto(tag)).thenReturn(responseDto);
        List<TagResponseDto> allByCompanyId = tagService.findAllByCompanyId(1);
        assertNotNull(allByCompanyId);
        assertTrue(allByCompanyId.contains(responseDto));
    }
}
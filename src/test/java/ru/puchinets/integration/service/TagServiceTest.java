package ru.puchinets.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.puchinets.annotation.IT;
import ru.puchinets.dto.response.TagResponseDto;
import ru.puchinets.service.TagService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@IT
class TagServiceTest {

    @Autowired
    TagService tagService;

    @Test
    void findAllByCompanyId() {
        List<TagResponseDto> byFirstCompany = tagService.findAllByCompanyId(1);
        assertNotNull(byFirstCompany);
        assertEquals(3, byFirstCompany.size());
        List<TagResponseDto> emptyByCompany = tagService.findAllByCompanyId(1000);
        assertNotNull(byFirstCompany);
        assertTrue(emptyByCompany.isEmpty());
    }
}
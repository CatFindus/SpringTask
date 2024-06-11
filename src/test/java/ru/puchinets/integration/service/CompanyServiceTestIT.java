package ru.puchinets.integration.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.puchinets.annotation.IT;
import ru.puchinets.dto.response.CompanyResponseDto;
import ru.puchinets.service.CompanyService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@IT
class CompanyServiceTestIT {

    @Autowired
    CompanyService companyService;

    @Test
    void findById() {
        Optional<CompanyResponseDto> byId = companyService.getById(1);
        assertTrue(byId.isPresent());
        assertEquals(1, byId.get().id());
        assertTrue(companyService.getById(1000).isEmpty());
    }

    @Test
    void findAll() {
        List<CompanyResponseDto> all = companyService.findAll();
        assertNotNull(all);
        assertEquals(3, all.size());
    }
}
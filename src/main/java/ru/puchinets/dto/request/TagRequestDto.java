package ru.puchinets.dto.request;


public record TagRequestDto(Long id,
                            String name,
                            String color,
                            String type,
                            Integer companyId) {

}

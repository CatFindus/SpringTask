package ru.puchinets.mapper;

public interface BaseMapper<RQ, RP, E> {
    RP toDto(E entity);

    E fromDto(RQ request);

    default E fromDto(RQ request, E entity) {
        return entity;
    }
}

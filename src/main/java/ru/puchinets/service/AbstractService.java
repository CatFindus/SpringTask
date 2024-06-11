package ru.puchinets.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import ru.puchinets.mapper.BaseMapper;
import ru.puchinets.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public abstract class AbstractService<RQ, RP, T, ID> {

    private final BaseRepository<T, ID> repository;
    private final BaseMapper<RQ, RP, T> mapper;

    public Optional<RP> getById(ID id) {
        return repository
                .findById(id)
                .map(mapper::toDto);
    }

    public List<RP> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public RP create(RQ request) {
        return Optional.of(request)
                .map(mapper::fromDto)
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow();
    }

    @Transactional
    public Optional<RP> update(ID id, RQ request) {
        return repository
                .findById(id)
                .map(entity -> mapper.fromDto(request, entity))
                .map(mapper::toDto);
    }

    @Transactional
    public boolean delete(ID id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    repository.flush();
                    return true;
                }).orElse(false);
    }

}

package ru.puchinets.entity;

public interface BaseEntity<ID> {
    ID getId();

    void setId(ID id);
}

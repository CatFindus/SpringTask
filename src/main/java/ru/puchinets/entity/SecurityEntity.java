package ru.puchinets.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class SecurityEntity<ID extends Number> implements BaseEntity<ID> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected ID id;
    @CreationTimestamp
    @Column(name = "created_at")
    protected LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;
}

package ru.puchinets.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.puchinets.enums.TagType;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag extends SecurityEntity<Long> {

    @Column(name = "name", nullable = false, length = 24)
    private String name;
    @Column(name = "color", nullable = false, length = 24)
    private String color;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TagType type;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}

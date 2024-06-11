package ru.puchinets.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends SecurityEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name", length = 64, unique = true, nullable = false)
    private String userName;
    @Column(name = "firstname", length = 64)
    private String firstname;
    @Column(name = "lastname", length = 64)
    private String lastname;
    @Column(name = "password", length = 128, nullable = false)
    private String password;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Task> tasks;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<Task> createdBY;
}

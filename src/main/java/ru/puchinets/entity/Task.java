package ru.puchinets.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.puchinets.enums.TaskStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task extends SecurityEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "content")
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    @Column(name = "task_status")
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;
    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;
}

package ru.puchinets.repository;

import org.springframework.stereotype.Repository;
import ru.puchinets.entity.Comment;

import java.util.List;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Long> {

    List<Comment> findAllByTaskIdOrderByCreatedAtDesc(Long taskId);
}

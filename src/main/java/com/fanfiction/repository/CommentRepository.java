package com.fanfiction.repository;

import com.fanfiction.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findAllByCompositionId(Long compositionId);
}

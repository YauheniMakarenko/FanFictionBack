package com.fanfiction.repository;

import com.fanfiction.models.Composition;
import com.fanfiction.models.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAllByComposition(Composition composition);

    List<Chapter> findAllByCompositionId(Long id);

    void deleteAllByComposition(Composition composition);
}

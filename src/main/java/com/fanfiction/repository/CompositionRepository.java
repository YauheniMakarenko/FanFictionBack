package com.fanfiction.repository;

import com.fanfiction.models.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition, Long> {

    List<Composition> findCompositionsByAuthorId(Long id);
}

package com.fanfiction.repository;

import com.fanfiction.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {


}

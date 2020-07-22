package com.fanfiction.repository;

import com.fanfiction.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

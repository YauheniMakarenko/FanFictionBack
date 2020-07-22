package com.fanfiction.service;

import com.fanfiction.DTO.CompositionDTO;
import com.fanfiction.DTO.CompositionHomeDTO;
import com.fanfiction.DTO.CompositionProfileDTO;
import com.fanfiction.DTO.GenreNewCompositionDTO;
import com.fanfiction.models.Composition;
import com.fanfiction.models.Genre;
import com.fanfiction.repository.ChapterRepository;
import com.fanfiction.repository.CompositionRepository;
import com.fanfiction.repository.GenreRepository;
import com.fanfiction.repository.UserRepository;
import com.fanfiction.security.securityservice.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompositionService {

    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired
    private GenreRepository genreRepository;

    public List<GenreNewCompositionDTO> allGenre() {
        return genreRepository.findAll().stream().map(genre -> new GenreNewCompositionDTO(genre.getId(),
                genre.getGenrename()))
                .collect(Collectors.toList());
    }

    public Long saveComposition(CompositionDTO compositionDTO, Authentication authentication) {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Composition composition = new Composition();

        composition.setTitle(compositionDTO.getTitle());
        composition.setDescription(compositionDTO.getDescription());
        composition.setGenres(compositionDTO.getGenres().stream()
                .map(genreNewCompositionDTO -> new Genre(genreNewCompositionDTO.getId(), genreNewCompositionDTO.getGenrename()))
                .collect(Collectors.toSet()));
        composition.setPublicationDate(String.valueOf(new java.sql.Timestamp(new Date().getTime())).replaceAll("\\.\\d+", ""));
        composition.setAuthor(userRepository.findByUsername(userDetails.getUsername()).get());
        if (compositionDTO.getId() != null) {
            composition.setId(compositionDTO.getId());
        }
        compositionRepository.save(composition);
        return composition.getId();
    }

    public CompositionDTO findCompositionById(Long id) {
        Composition composition = compositionRepository.findById(id).get();
        return new CompositionDTO(composition.getId(),
                composition.getTitle(),
                composition.getDescription(),
                composition.getGenres().stream().map(genre -> new GenreNewCompositionDTO(
                        genre.getId(),
                        genre.getGenrename())).collect(Collectors.toSet()),
                composition.getAuthor());
    }


    public void deleteComposition(Long compositionId) {
        compositionRepository.deleteById(compositionId);
    }

    public List<CompositionProfileDTO> getCompositionsForCurrentUser(Authentication authentication) {
        return compositionRepository.findCompositionsByAuthorId(
                userRepository.findByUsername(authentication.getName()).get().getId()).stream()
                .map(composition ->
                        new CompositionProfileDTO(
                                composition.getId(),
                                composition.getTitle(),
                                composition.getDescription(),
                                chapterRepository.findAllByComposition(composition).size()))
                .collect(Collectors.toList());
    }

    public List<CompositionHomeDTO> getAllComposition() {
        return compositionRepository.findAll().stream().map(composition -> new CompositionHomeDTO(composition.getId(),
                composition.getTitle(),
                composition.getDescription(),
                composition.getPublicationDate(),
                composition.getGenres()))
                .sorted((composition1, composition2) -> {
                    try {
                        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(composition2.getPublicationDate())
                                .compareTo(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(composition1.getPublicationDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 0;
                }).collect(Collectors.toList());
    }
}


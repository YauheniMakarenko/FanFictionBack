package com.fanfiction.service;

import com.fanfiction.DTO.CompositionDTO;
import com.fanfiction.models.Composition;
import com.fanfiction.models.Genre;
import com.fanfiction.repository.ChapterRepository;
import com.fanfiction.repository.CompositionRepository;
import com.fanfiction.repository.GenreRepository;
import com.fanfiction.repository.UserRepository;
import com.fanfiction.security.securityservice.UserDetailsImpl;
import com.fanfiction.payload.request.CompositionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    public Composition saveComposition(CompositionRequest compositionRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<Genre> compositionGenres = allGenre().stream()
                .filter(genre -> compositionRequest.getGenres().contains(genre.getGenrename())).collect(Collectors.toSet());

        Composition composition = new Composition();
        if (compositionRequest.getId() != null){
            composition.setId(compositionRequest.getId());
        }
        composition.setTitle(compositionRequest.getTitle());
        composition.setDescription(compositionRequest.getDescription());
        composition.setGenres(compositionGenres);
        composition.setPublicationDate(String.valueOf(new java.sql.Timestamp(new Date().getTime())).replaceAll("\\.\\d+", ""));
        composition.setAuthor(userRepository.findByUsername(userDetails.getUsername()).get());
        compositionRepository.save(composition);
        return composition;
    }

    public List<Genre> allGenre() {
        return genreRepository.findAll();
    }


    public Composition findCompositionById(Long id) {
        return compositionRepository.findById(id).get();
    }


    public void deleteComposition(Long compositionId) {
        compositionRepository.deleteById(compositionId);
    }

    public List<CompositionDTO> getCompositionsForCurrentUser(Authentication authentication){
        return compositionRepository.findCompositionsByAuthorId(
                userRepository.findByUsername(authentication.getName()).get().getId()).stream()
                .map(composition ->
                        new CompositionDTO(
                                composition.getId(),
                                composition.getTitle(),
                                composition.getDescription(),
                                chapterRepository.findAllByComposition(composition).size()))
                .collect(Collectors.toList());
    }

    public List<Composition> getAllComposition() {
        return compositionRepository.findAll();
    }
}


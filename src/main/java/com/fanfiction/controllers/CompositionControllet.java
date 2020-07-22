package com.fanfiction.controllers;

import com.fanfiction.DTO.CompositionDTO;
import com.fanfiction.DTO.CompositionHomeDTO;
import com.fanfiction.DTO.CompositionProfileDTO;
import com.fanfiction.models.Composition;
import com.fanfiction.service.CompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/test")
public class CompositionControllet {

    @Autowired
    private CompositionService compositionService;

    @GetMapping("/allCompositions")
    public List<CompositionHomeDTO> allCompositions() {
        return compositionService.getAllComposition();
    }

    @PostMapping("/savecomposition")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Long saveComposition(@Valid @RequestBody CompositionDTO compositionDTO, Authentication authentication) {
        return compositionService.saveComposition(compositionDTO, authentication);
    }

    @GetMapping("/getcomposition/{compositionId}")
    public CompositionDTO getComposition(@PathVariable Long compositionId) {
        return compositionService.findCompositionById(compositionId);
    }

    @DeleteMapping("/deletecomposition/{compositionId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void deleteComposition(@PathVariable Long compositionId){
        compositionService.deleteComposition(compositionId);
    }

    @GetMapping("/getcompositionsforcurrentuser")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<CompositionProfileDTO> getCompositionsForCurrentUser(Authentication authentication){
        return compositionService.getCompositionsForCurrentUser(authentication);
    }
}

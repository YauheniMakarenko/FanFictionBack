package com.fanfiction.controllers;

import com.fanfiction.DTO.CommentsDTO;
import com.fanfiction.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fanfic")
public class CommentController {

    @Autowired
    private CommentService commentService;

    private final SimpMessagingTemplate template;

    @Autowired
    CommentController(SimpMessagingTemplate template){
        this.template = template;
    }

    @GetMapping("/getCommentsByCompositionId/{compositionId}")
    public List<CommentsDTO> getComposition(@PathVariable Long compositionId) {
        return commentService.getCommentsByCompositionId(compositionId);
    }

    @PostMapping("/addcomment")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void saveComment(@RequestBody CommentsDTO commentsDTO, Authentication authentication){
        this.template.convertAndSend("/message", commentService.saveComment(commentsDTO, authentication));
    }
}

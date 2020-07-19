package com.fanfiction.controllers;

import com.fanfiction.DTO.CommentsDTO;
import com.fanfiction.models.Comments;
import com.fanfiction.payload.request.CommentRequest;
import com.fanfiction.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
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
    public void saveComment(@RequestBody CommentRequest commentRequest, Authentication authentication){
        this.template.convertAndSend("/message", commentService.saveComment(commentRequest, authentication));
    }
}

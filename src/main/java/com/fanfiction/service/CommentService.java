package com.fanfiction.service;

import com.fanfiction.DTO.CommentsDTO;
import com.fanfiction.models.Comments;
import com.fanfiction.payload.request.CommentRequest;
import com.fanfiction.repository.CommentRepository;
import com.fanfiction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentsDTO> getCommentsByCompositionId(Long compositionId) {
        return commentRepository.findAllByCompositionId(compositionId).stream()
                .map(comment -> new CommentsDTO(comment.getId(),
                        comment.getText(), comment.getCommentUser(), comment.getComposition()))
                .sorted((comment1, comment2) -> Integer.parseInt(String.valueOf(comment2.getId() - comment1.getId())))
                .collect(Collectors.toList());
    }

    public Comments saveComment(CommentRequest commentRequest, Authentication authentication){
        Comments comment = new Comments();
        comment.setCommentUser(userRepository.findByUsername(authentication.getName()).get());
        comment.setText(commentRequest.getText());
        comment.setComposition(commentRequest.getComposition());
        commentRepository.save(comment);
        return comment;
    }
}

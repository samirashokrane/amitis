package com.amitis.interview.controller;

import com.amitis.interview.dto.request.CommentFilterRequest;
import com.amitis.interview.dto.request.CommentRegisterRequest;
import com.amitis.interview.dto.request.CommentUpdateRequest;
import com.amitis.interview.dto.response.CommentFilterResponse;
import com.amitis.interview.dto.response.CommentResponse;
import com.amitis.interview.service.facade.CommentFacadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/comment")
@AllArgsConstructor
public class CommentRestEndPoint {

    private final CommentFacadeService commentFacadeService;

    @GetMapping("/")
    public ResponseEntity<CommentFilterResponse> get(final @ModelAttribute CommentFilterRequest request) {
        return new ResponseEntity<>(commentFacadeService.list(request), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CommentResponse> create(final @PathVariable(name = "id") Integer postId,
            final @RequestBody CommentRegisterRequest request) {
        request.setPostId(postId);
        return new ResponseEntity<>(commentFacadeService.create(request), HttpStatus.OK);
    }

    @PatchMapping("/Comments/{pid}/{cid}")
    public ResponseEntity<CommentResponse> update(final @PathVariable(name = "id") Integer commentId,
                                               final @RequestBody CommentUpdateRequest request) {
        return new ResponseEntity<>(commentFacadeService.update(commentId , request), HttpStatus.OK);
    }

    @DeleteMapping("/Comments/{id}")
    public ResponseEntity delete(final @PathVariable("id") Integer commentId) {
        commentFacadeService.delete(commentId);
        return new ResponseEntity(HttpStatus.OK);

    }
}

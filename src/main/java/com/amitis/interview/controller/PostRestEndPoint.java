package com.amitis.interview.controller;

import com.amitis.interview.dto.request.PostFilterRequest;
import com.amitis.interview.dto.request.PostRegisterRequest;
import com.amitis.interview.dto.request.PostUpdateRequest;
import com.amitis.interview.dto.response.CommentsResponse;
import com.amitis.interview.dto.response.PostFilterResponse;
import com.amitis.interview.dto.response.PostResponse;
import com.amitis.interview.service.facade.PostFacadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/post")
@AllArgsConstructor
public class PostRestEndPoint {

    private final PostFacadeService postFacadeService;

    @GetMapping("/")
    public ResponseEntity<PostFilterResponse> get(final @ModelAttribute PostFilterRequest request) {
        return new ResponseEntity<>(postFacadeService.get(request), HttpStatus.OK);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponse> posts(final @PathVariable("id") Integer id) {
        return new ResponseEntity<>(postFacadeService.posts(id), HttpStatus.OK);
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<CommentsResponse> postComments(final @PathVariable("id") Integer id) {
        return new ResponseEntity<>(postFacadeService.postComments(id), HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<PostResponse> create(
            final @RequestBody PostRegisterRequest request) {
        return new ResponseEntity<>(postFacadeService.create(request), HttpStatus.OK);
    }

    @PatchMapping("/posts/{id}")
    public ResponseEntity<PostResponse> update(final @PathVariable(name = "id") Integer postId,
                                                                      final @RequestBody PostUpdateRequest request) {
        return new ResponseEntity<>(postFacadeService.update(postId ,request), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity delete(final @PathVariable("id") Integer postId) {
        postFacadeService.delete(postId);
        return new ResponseEntity(HttpStatus.OK);

    }
}

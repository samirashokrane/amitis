package com.amitis.interview.service.mapper;

import com.amitis.interview.dto.request.CommentRegisterRequest;
import com.amitis.interview.dto.request.CommentUpdateRequest;
import com.amitis.interview.dto.response.CommentFilterResponse;
import com.amitis.interview.dto.response.CommentResponse;
import com.amitis.interview.dto.response.CommentsResponse;
import com.amitis.interview.model.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommentMapper {

    public static final CommentMapper INSTANCE = new CommentMapper();
    ObjectMapper mapper = new ObjectMapper();

    public Comment initialize(final CommentResponse request) {
        return Comment.builder()
                .id(request.getId())
                .postId(request.getPostId())
                .name(request.getName())
                .email(request.getEmail())
                .body(request.getBody())
                .isDeleted(false)
                .version(0L)
                .build();
    }

    public CommentsResponse getResponse(final Object request) {
        /**
         // jackson's objectmapper
         **/
        CommentResponse[] apiCommentRespons = mapper.convertValue(request, CommentResponse[].class);
        List<CommentResponse> commentResponseList = Arrays.asList(apiCommentRespons);
        return CommentsResponse.builder().apiCommentRespons(commentResponseList).build();
    }

    public List<Comment> convertToEntity(final CommentsResponse commentsResponse) {
        List<Comment> commentList = new ArrayList<>();
        commentsResponse.getApiCommentRespons().forEach(comment -> {
            commentList.add(initialize(comment));
        });
        return commentList;
    }
    public CommentResponse getResponse(final Comment comment) {

        return CommentResponse.builder().id(comment.getId())
                .body(comment.getBody())
                .name(comment.getName())
                .postId(comment.getPostId())
                .id(comment.getId())
                .email(comment.getEmail())
                .build();
    }
    public CommentFilterResponse getFilterResponse(final Page<Comment> commentPage){
        return CommentFilterResponse.builder()
                .commentResponses(commentPage.map(this::getResponse))
                .build();
    }
    public Comment create(final CommentRegisterRequest request) {
        return Comment.builder()
                .postId(request.getPostId())
                .body(request.getBody())
                .name(request.getName())
                .email(request.getEmail())
                .isDeleted(false)
                .version(0L)
                .build();
    }

    public Comment update(final Comment comment , final CommentUpdateRequest commentUpdateRequest) {
        comment.setBody(commentUpdateRequest.getBody());
        comment.setEmail(commentUpdateRequest.getEmail());
        comment.setName(commentUpdateRequest.getName());
        return comment;
    }

    public CommentsResponse getCommentResponse(List<Comment> commentList) {
        commentList.forEach(comment -> getResponse(comment));

        List<CommentResponse> commentResponses = new ArrayList<CommentResponse>();
        commentList.forEach(comment -> {
            commentResponses.add(getResponse(comment));
        });
       return CommentsResponse.builder().apiCommentRespons(commentResponses).build();

    }
}

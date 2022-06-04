package com.amitis.interview.service.facade;

import com.amitis.interview.dto.request.PostFilterRequest;
import com.amitis.interview.dto.request.PostRegisterRequest;
import com.amitis.interview.dto.request.PostUpdateRequest;
import com.amitis.interview.dto.response.CommentsResponse;
import com.amitis.interview.dto.response.PostFilterResponse;
import com.amitis.interview.dto.response.PostResponse;
import com.amitis.interview.model.Comment;
import com.amitis.interview.model.Post;
import com.amitis.interview.repository.PostRepository;
import com.amitis.interview.service.exception.logic.ReadableServiceException;
import com.amitis.interview.service.helper.PostPredicateBuilder;
import com.amitis.interview.service.mapper.CommentMapper;
import com.amitis.interview.service.mapper.PostMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class PostFacadeServiceImpl implements PostFacadeService {

    private PostRepository postRepository;

    @Override
    public PostFilterResponse get(PostFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by(request.getSortOrder()));

        BooleanExpression booleanExpression = PostPredicateBuilder.getPredicate(request);
        Page<Post> postPage = postRepository.findAll(booleanExpression, pageable);
        return PostMapper.INSTANCE.getFilterResponse(postPage);
    }

    @Override
    public PostResponse posts(final Integer id) {
        Optional<Post> optionalPost = Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(() -> ReadableServiceException.notFoundException(id.toString(), "post-id", HttpStatus.NOT_FOUND)));
        return PostMapper.INSTANCE.getResponse(optionalPost.get());
    }

    @Override
    public CommentsResponse postComments(final Integer id) {
        Optional<Post> optionalPost = Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(() -> ReadableServiceException.notFoundException(id.toString(), "post-id", HttpStatus.NOT_FOUND)));
        List<Comment> commentList = postRepository.findByPostId(id);
        CommentsResponse commentsResponse = CommentMapper.INSTANCE.getCommentResponse(commentList);
        return commentsResponse;
    }

    public PostResponse create(final PostRegisterRequest request) {

        Post post = postRepository.save(PostMapper.INSTANCE.create(request));
        log.info("post....{}", post);
        return PostMapper.INSTANCE.getResponse(post);
    }

    public PostResponse update(final Integer id, final PostUpdateRequest postUpdateRequest) {
        Optional<Post> optionalPost = Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(() -> ReadableServiceException.notFoundException(id.toString(), "post-id", HttpStatus.NOT_FOUND)));
        Post post = PostMapper.INSTANCE.update(optionalPost.get(), postUpdateRequest);
        return PostMapper.INSTANCE.getResponse(postRepository.save(post));
    }

    public void delete(final Integer id) {
        Optional<Post> optionalPost = Optional.ofNullable(postRepository.findById(id)
                .orElseThrow(() -> ReadableServiceException.notFoundException(id.toString(), "post-id", HttpStatus.NOT_FOUND)));
        optionalPost.get().setIsDeleted(true);
        postRepository.save(optionalPost.get());
    }
}

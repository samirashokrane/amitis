package com.amitis.interview.service.facade;

import com.amitis.interview.dto.request.CommentFilterRequest;
import com.amitis.interview.dto.request.CommentRegisterRequest;
import com.amitis.interview.dto.request.CommentUpdateRequest;
import com.amitis.interview.dto.response.CommentFilterResponse;
import com.amitis.interview.dto.response.CommentResponse;
import com.amitis.interview.model.Comment;
import com.amitis.interview.repository.CommentRepository;
import com.amitis.interview.service.exception.logic.ReadableServiceException;
import com.amitis.interview.service.helper.CommentPredicateBuilder;
import com.amitis.interview.service.mapper.CommentMapper;
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

import java.util.Optional;

@Log4j2
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CommentFacadeServiceImpl implements CommentFacadeService {

    private CommentRepository commentRepository;

    @Override
    public CommentFilterResponse list(CommentFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by(request.getSortOrder()));

        BooleanExpression booleanExpression = CommentPredicateBuilder.getPredicate(request);
        Page<Comment> commentPage = commentRepository.findAll(booleanExpression, pageable);
        return CommentMapper.INSTANCE.getFilterResponse(commentPage);
    }

    public CommentResponse create(final CommentRegisterRequest request) {

        Comment comment = commentRepository.save(CommentMapper.INSTANCE.create(request));
        log.info("post....{}", comment);
        return CommentMapper.INSTANCE.getResponse(comment);
    }

    public CommentResponse update(final Integer id, final CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> optionalComment = Optional.ofNullable(commentRepository.findById(id)
                .orElseThrow(() -> ReadableServiceException.notFoundException(id.toString(), "post-id", HttpStatus.NOT_FOUND)));
        Comment comment = CommentMapper.INSTANCE.update(optionalComment.get(), commentUpdateRequest);
        return CommentMapper.INSTANCE.getResponse(commentRepository.save(comment));
    }

    public void delete(final Integer id) {
        Optional<Comment> optionalComment = Optional.ofNullable(commentRepository.findById(id)
                .orElseThrow(() -> ReadableServiceException.notFoundException(id.toString(), "comment-id", HttpStatus.NOT_FOUND)));
        optionalComment.get().setIsDeleted(true);
        commentRepository.save(optionalComment.get());
    }


}

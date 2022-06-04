package com.amitis.interview.service.facade;

import com.amitis.interview.dto.request.CommentFilterRequest;
import com.amitis.interview.dto.request.CommentRegisterRequest;
import com.amitis.interview.dto.request.CommentUpdateRequest;
import com.amitis.interview.dto.response.CommentFilterResponse;
import com.amitis.interview.dto.response.CommentResponse;

public interface CommentFacadeService {

    CommentFilterResponse list(CommentFilterRequest request);

    CommentResponse create(final CommentRegisterRequest request);

    CommentResponse update(final Integer id, final CommentUpdateRequest request);

    void delete(final Integer id);
}

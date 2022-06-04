package com.amitis.interview.service.facade;

import com.amitis.interview.dto.request.PostFilterRequest;
import com.amitis.interview.dto.request.PostRegisterRequest;
import com.amitis.interview.dto.request.PostUpdateRequest;
import com.amitis.interview.dto.response.CommentsResponse;
import com.amitis.interview.dto.response.PostFilterResponse;
import com.amitis.interview.dto.response.PostResponse;

public interface PostFacadeService {

    PostFilterResponse get(PostFilterRequest request);

    PostResponse posts(final Integer id);

    CommentsResponse postComments(final Integer id);

    PostResponse create(final PostRegisterRequest request);

    PostResponse update(final Integer id, final PostUpdateRequest request);

    void delete(final Integer id);
}

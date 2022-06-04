package com.amitis.interview.service.mapper;

import com.amitis.interview.dto.request.PostRegisterRequest;
import com.amitis.interview.dto.request.PostUpdateRequest;
import com.amitis.interview.dto.response.PostFilterResponse;
import com.amitis.interview.dto.response.PostResponse;
import com.amitis.interview.dto.response.PostsResponse;
import com.amitis.interview.model.Post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostMapper {

    public static final PostMapper INSTANCE = new PostMapper();
    ObjectMapper mapper = new ObjectMapper();

    public Post initialize(final PostResponse request) {
        return Post.builder()
                .id(request.getId())
                .userId(request.getUserId())
                .title(request.getTitle())
                .body(request.getBody())
                .isDeleted(false)
                .version(0L)
                .build();
    }

    public Post create(final PostRegisterRequest request) {
        return Post.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .body(request.getBody())
                .isDeleted(false)
                .version(0L)
                .build();
    }
    public Post update(final Post post ,final PostUpdateRequest postUpdateRequest) {
        post.setBody(postUpdateRequest.getBody());
        post.setTitle(postUpdateRequest.getTitle());
       return post;
    }
    public PostsResponse getObjectResponse(final Object request) {
        /**
         // jackson's objectmapper
         **/
        PostResponse[] apiPostRespons = mapper.convertValue(request, PostResponse[].class);
        List<PostResponse> postResponseList = Arrays.asList(apiPostRespons);
        return PostsResponse.builder().apiPostRespons(postResponseList).build();
    }

    public List<Post> convertToEntity(final PostsResponse postsResponse) {
        List<Post> postList = new ArrayList<Post>();
        postsResponse.getApiPostRespons().forEach(Post -> {
            postList.add(initialize(Post));
        });
        return postList;
    }
    public PostResponse getResponse(final Post post) {

        return PostResponse.builder().id(post.getId())
                .body(post.getBody())
                .title(post.getTitle())
                .userId(post.getUserId())
                .id(post.getId())
                .build();
    }
    public PostFilterResponse getFilterResponse(final Page<Post> postPage){
        return PostFilterResponse.builder()
                .postResponses(postPage.map(this::getResponse))
                .build();
    }

}

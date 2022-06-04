package com.amitis.interview.service.mapper;

import com.amitis.interview.model.UserProfile;
import com.amitis.interview.dto.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserMapper {

    public static final UserMapper INSTANCE = new UserMapper();
    ObjectMapper mapper = new ObjectMapper();

    public UserProfile initialize(final UserResponse request) {
        return UserProfile.builder()
                .id(request.getId())
                .name(request.getName())
                .isDeleted(false)
                .version(0L)
                .build();
    }

}

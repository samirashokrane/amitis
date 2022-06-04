package com.amitis.interview.dto.request;

import com.amitis.interview.dto.BaseRequestDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder(toBuilder = true)
public class CommentUpdateRequest extends BaseRequestDto {


    String name;

    String email;

    String body;

}
package com.amitis.interview.dto.response;

import com.amitis.interview.dto.BaseResponseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;


@Data
@ToString
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TodoFilterResponse extends BaseResponseDto {

    Page<TodoResponse> todoResponses;

}

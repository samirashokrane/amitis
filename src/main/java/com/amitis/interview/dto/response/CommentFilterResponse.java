package com.amitis.interview.dto.response;

import com.amitis.interview.dto.BaseResponseDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;


@Data
@ToString
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentFilterResponse extends BaseResponseDto {

    Page<CommentResponse> commentResponses;

}

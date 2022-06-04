package com.amitis.interview.dto.response;

import com.amitis.interview.dto.BaseResponseDto;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Data
@ToString
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class PostCommentsResponse extends BaseResponseDto {

  CommentsResponse commentsResponse;
}

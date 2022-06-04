package com.amitis.interview.dto.response;

import com.amitis.interview.dto.BaseResponseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Data
@ToString
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse extends BaseResponseDto {

    @JsonProperty("userId")
    Integer userId;

    @JsonProperty("id")
    Integer id;

    @JsonProperty("title")
    String title;

    @JsonProperty("completed")
    Boolean completed;

  /*  {
        "userId": 1,
            "id": 1,
            "title": "delectus aut autem",
            "completed": false
    }*/

}

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
public class UserResponse extends BaseResponseDto {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("name")
    String name;

}

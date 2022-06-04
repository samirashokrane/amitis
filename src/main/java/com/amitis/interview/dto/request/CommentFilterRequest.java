package com.amitis.interview.dto.request;


import com.amitis.interview.dto.BaseFilterRequestDto;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@ToString
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentFilterRequest extends BaseFilterRequestDto {
    private static final long serialVersionUID = 1L;


}

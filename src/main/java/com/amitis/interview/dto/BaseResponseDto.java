package com.amitis.interview.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseResponseDto extends BaseDto {

    private static final long serialVersionUID = 1L;
}

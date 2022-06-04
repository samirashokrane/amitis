package com.amitis.interview.dto;

import com.amitis.interview.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public abstract class BaseRequestDto extends BaseDto {

	private static final long serialVersionUID = 1L;

}

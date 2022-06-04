package com.amitis.interview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

}
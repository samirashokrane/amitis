package com.amitis.interview.dto;

import com.amitis.interview.model.enums.SortOrderEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseSortDto {

    Integer priority;
    String parameter;
    SortOrderEnum sortOrder;
}

package com.amitis.interview.dto;


import com.amitis.interview.model.enums.SortOrderEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
@ToString
@SuperBuilder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseFilterRequestDto extends BaseDto {

    private static final long serialVersionUID = 1L;

    Integer pageNumber = 0;

    Integer pageSize = 10;

    List<BaseSortDto> sortParams = new ArrayList<>();


    public <T> List<Sort.Order> getSortOrder() {
        if (sortParams==null){
            sortParams = new ArrayList<>();
        }
        List<Sort.Order> sortOrderList = new ArrayList<>();

        Sort.Direction order = Sort.Direction.DESC;
        Sort.Order sortOrder = new Sort.Order(order, "id");
        sortOrderList.add(sortOrder);

        for (BaseSortDto prioritizedParam : sortParams) {
            sortParams.sort(Comparator.comparing(BaseSortDto::getPriority));
            order = prioritizedParam.getSortOrder().equals(SortOrderEnum.ASC.toString()) ? Sort.Direction.ASC : Sort.Direction.DESC;
             sortOrder = new Sort.Order(order, prioritizedParam.getParameter());
            sortOrderList.add(sortOrder);
        }
        return sortOrderList;
    }


}

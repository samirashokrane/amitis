package com.amitis.interview.service.facade;

import com.amitis.interview.dto.request.TodoFilterRequest;
import com.amitis.interview.dto.response.TodoFilterResponse;
import com.amitis.interview.dto.response.TodosResponse;
import com.amitis.interview.model.Todo;
import com.amitis.interview.repository.TodoRepository;
import com.amitis.interview.service.exception.logic.ReadableServiceException;
import com.amitis.interview.service.helper.TodoPredicateBuilder;
import com.amitis.interview.service.mapper.TodoMapper;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TodoFacadeServiceImpl implements TodoFacadeService {

    private TodoRepository todoRepository;

    @Override
    public TodosResponse list() {
        
        TodosResponse todosResponse = TodoMapper.INSTANCE.convertToResponse(todoRepository.findAll());
        if (todosResponse == null) {
            ReadableServiceException.notFoundException("todo is null", "null", HttpStatus.NOT_FOUND);
        }
        return todosResponse;
    }


    @Override
    public TodoFilterResponse todos(TodoFilterRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize(), Sort.by(request.getSortOrder()));

        BooleanExpression booleanExpression = TodoPredicateBuilder.getPredicate(request);
        Page<Todo> todoPage = todoRepository.findAll(booleanExpression, pageable);
        return TodoMapper.INSTANCE.getFilterResponse(todoPage);
    }

}

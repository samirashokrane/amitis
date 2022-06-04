package com.amitis.interview.service.mapper;

import com.amitis.interview.dto.response.TodoFilterResponse;
import com.amitis.interview.dto.response.TodoResponse;
import com.amitis.interview.dto.response.TodosResponse;
import com.amitis.interview.model.Todo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TodoMapper {

    public static final TodoMapper INSTANCE = new TodoMapper();
    ObjectMapper mapper = new ObjectMapper();

    public Todo initialize(final TodoResponse request) {
        return Todo.builder()
                .id(request.getId())
                .userId(request.getUserId())
                .title(request.getTitle())
                .completed(request.getCompleted())
                .isDeleted(false)
                .version(0L)
                .build();
    }

    public TodosResponse getObjectResponse(final Object request) {
        /**
         // jackson's objectmapper
         **/
        TodoResponse[] apiTodoRespons = mapper.convertValue(request, TodoResponse[].class);
        List<TodoResponse> apiTodoResponseList = Arrays.asList(apiTodoRespons);
        return TodosResponse.builder().todoResponses(apiTodoResponseList).build();
    }

    public List<Todo> convertToEntity(final TodosResponse TodosResponse) {
        List<Todo> TodoList = new ArrayList<Todo>();
        TodosResponse.getTodoResponses().forEach(Todo -> {
            TodoList.add(initialize(Todo));
        });
        return TodoList;
    }

    public TodosResponse convertToResponse(final List<Todo> todoList) {
        List<TodoResponse> todoResponseList = new ArrayList<TodoResponse>();
        todoList.forEach(Todo -> {
            todoResponseList.add(getResponse(Todo));
        });
        return TodosResponse.builder().todoResponses(todoResponseList).build();
    }

    public TodoResponse getResponse(final Todo todo) {

        return TodoResponse.builder().id(todo.getId())
                .completed(todo.isCompleted())
                .title(todo.getTitle())
                .userId(todo.getUserId())
                .id(todo.getId())
                .build();
    }

    public TodoFilterResponse getFilterResponse(final Page<Todo> todoPage){
        return TodoFilterResponse.builder()
                .todoResponses(todoPage.map(this::getResponse))
                .build();
    }
}

package com.amitis.interview.controller;

import com.amitis.interview.dto.request.TodoFilterRequest;
import com.amitis.interview.dto.response.TodoFilterResponse;
import com.amitis.interview.dto.response.TodosResponse;
import com.amitis.interview.service.facade.TodoFacadeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/todo")
@AllArgsConstructor
public class TodoRestEndPoint {

    private final TodoFacadeService todoFacadeService;

    @GetMapping("/")
    public ResponseEntity<TodosResponse> get() {
        return new ResponseEntity<>(todoFacadeService.list(), HttpStatus.OK);
    }

    @GetMapping("/todos")
    public ResponseEntity<TodoFilterResponse> todos(final @ModelAttribute TodoFilterRequest request) {
        return new ResponseEntity<>(todoFacadeService.todos(request), HttpStatus.OK);
    }

}

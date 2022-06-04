package com.amitis.interview.service.facade;

import com.amitis.interview.dto.request.TodoFilterRequest;
import com.amitis.interview.dto.response.TodoFilterResponse;
import com.amitis.interview.dto.response.TodosResponse;

public interface TodoFacadeService {

    TodosResponse list();

    TodoFilterResponse todos(TodoFilterRequest request);
}

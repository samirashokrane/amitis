package com.amitis.interview.service.helper;

import com.amitis.interview.dto.request.TodoFilterRequest;
import com.amitis.interview.model.QTodo;
import com.amitis.interview.model.expressionbuilder.OptionalBooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

public class TodoPredicateBuilder {

    public static BooleanExpression getPredicate(final TodoFilterRequest request) {


        QTodo qTodo = QTodo.todo;

        BooleanExpression predicate = new OptionalBooleanBuilder(qTodo.isNotNull(), true)
                .notNullAnd(qTodo.userId::eq, request.getUserId())
                .notNullAnd(qTodo.completed::eq, request.isCompleted())
                .build();
        return predicate;
    }
}

package com.amitis.interview.service.helper;

import com.amitis.interview.dto.request.CommentFilterRequest;
import com.amitis.interview.model.QComment;
import com.amitis.interview.model.expressionbuilder.OptionalBooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

public class CommentPredicateBuilder {

    public static BooleanExpression getPredicate(final CommentFilterRequest request) {


        QComment qComment = QComment.comment;

        BooleanExpression predicate = new OptionalBooleanBuilder(qComment.isNotNull(), true)
                .build();
        return predicate;
    }
}
